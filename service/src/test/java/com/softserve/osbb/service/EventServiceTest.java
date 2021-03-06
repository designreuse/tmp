package com.softserve.osbb.service;

import com.softserve.osbb.ServiceAppConfiguration;
import com.softserve.osbb.tenants.model.Event;
import com.softserve.osbb.tenants.model.Osbb;
import com.softserve.osbb.tenants.model.enums.Periodicity;
import com.softserve.osbb.tenants.service.EventService;
import com.softserve.osbb.tenants.service.OsbbService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by nataliia on 18.07.16.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ServiceAppConfiguration.class)
@Rollback
@Transactional
public class EventServiceTest {
    
    private Event event;
    private Event event1;

    @Autowired
    private EventService eventService;
    
    @Autowired
    private OsbbService osbbService;
    
    @Before
    public void init() {
        Osbb osbb = new Osbb();
        osbb.setName("Test OSBB");
        osbbService.addOsbb(osbb);

        event = new Event();
        event.setName("Trash droping.");
        event.setAuthor("Main OSBB");
        event.setOsbb(osbb);
        event.setDescription("Simple repeatable trash recycling.");
        event.setPeriodicity(Periodicity.PERMANENT_WEEKLY);
        event.setDate(LocalDate.now());

        event1 = new Event();
        event1.setName("Charity festival.");
        event1.setAuthor("City Council");
        event1.setOsbb(osbb);
        event1.setDescription("Charity festival for homelesspeople.");
        event1.setPeriodicity(Periodicity.ONE_TIME);
        event1.setDate(LocalDate.now());
    }

    @Test
    public void testSave() {
        eventService.saveEvent(event);
        assertEquals(event, eventService.getEventById(event.getEventId()));
    }

    @Test
    public void testSaveList() {
        List<Event> list = new ArrayList<>();
        list.add(event);
        list.add(event1);
        eventService.saveEvents(list);
        assertTrue(eventService.getAllEvents().containsAll(list));
    }

    @Test
    public void testFindOne() {
        eventService.saveEvent(event);
        assertEquals(event, eventService.getEventById(event.getEventId()));
    }

    @Test
    public void testFindEvents() {
        List<Event> list = new ArrayList<>();
        list.add(event1);
        list.add(event);
        eventService.saveEvents(list);
        assertTrue(eventService.getEvents(list).containsAll(list));
    }

    @Test
    public void testFindAllByIDs() {
        List<Event> list = new ArrayList<>();
        list.add(event1);
        list.add(event);
        eventService.saveEvents(list);
        List<Integer> ids = new ArrayList<>();
        ids.add(event.getEventId());
        ids.add(event1.getEventId());
        assertTrue(eventService.getEventsByIds(ids).containsAll(list));
    }

    @Test
    public void testFindAll() {
        List<Event> list = new ArrayList<>();
        list.add(event);
        list.add(event1);
        eventService.saveEvent(event);
        eventService.saveEvent(event1);
        assertTrue(eventService.getAllEvents().containsAll(list));
    }

    @Test
    public void testDelete() {
        eventService.saveEvent(event);
        eventService.deleteEvent(event);
        assertFalse(eventService.existsEvent(event.getEventId()));
    }

    @Test
    public void testDeleteById() {
        eventService.saveEvent(event);
        eventService.deleteEventById(event.getEventId());
        assertFalse(eventService.existsEvent(event.getEventId()));
    }

    @Test
    public void testDeleteList() {
        List<Event> list = new ArrayList<>();
        list.add(event);
        list.add(event1);
        eventService.saveEvents(list);
        eventService.deleteEvents(list);
        assertFalse(eventService.existsEvent(event.getEventId()));
        assertFalse(eventService.existsEvent(event1.getEventId()));
    }

    @Test
    public void testDeleteAll() {
        eventService.saveEvent(event);
        eventService.saveEvent(event1);
        eventService.deleteAllEvents();
        assertTrue(eventService.getAllEvents().isEmpty());
    }

    @Test
    public void testCount() {
        int before = eventService.getAllEvents().size();
        eventService.saveEvent(event);
        eventService.saveEvent(event1);
        assertEquals(before + 2, eventService.countEvents());
    }

    @Test
    public void testExists() {
        eventService.saveEvent(event);
        assertTrue(eventService.existsEvent(event.getEventId()));
    }
    
}