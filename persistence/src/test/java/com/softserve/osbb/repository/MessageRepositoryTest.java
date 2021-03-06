package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceAppConfiguration;
import com.softserve.osbb.tenants.model.Message;
import com.softserve.osbb.tenants.model.User;
import com.softserve.osbb.tenants.repository.MessageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * Created by Kris on 05.07.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceAppConfiguration.class)
@Transactional
public class MessageRepositoryTest {

    private Message message;
    private User user;
    @Autowired
    MessageRepository messageRepository;

    @Before
    public void init() {
        message = new Message();

        user = new User();
        user.setBirthDate(new Date());
        user.setEmail("blabla@gmail.com");
        user.setFirstName("Vanya");
        user.setLastName("Popov");
        user.setGender("man");
        user.setPassword("234qwer");

        message.setUser(user);
        message.setMessage("Hi! This is a first message in our database.");
        message.setDescription("some description");
        message.setTime(LocalDate.now());

    }

    @Test
    public void testSave() {
        Assert.assertNull(message.getMessageId());
        messageRepository.save(message);
        Assert.assertNotNull(message.getMessageId());
    }

    @Test
    public void testDeleteMessageById() {
        messageRepository.save(message);
        messageRepository.delete(message.getMessageId());
        Assert.assertFalse(messageRepository.exists(message.getMessageId()));
    }

    @Test
    public void testGetAllMessage() {
        List<Message> list = Arrays.asList(new Message(), new Message(), new Message());
        messageRepository.save(list);
        Assert.assertTrue(list.size() == messageRepository.findAll().size());
    }

    @Test
    public void testDeleteByMessage() {
        messageRepository.save(message);
        messageRepository.delete(message);
        Assert.assertFalse(messageRepository.exists(message.getMessageId()));
    }

    @Test
    public void testDeleteAllMessages() {
        messageRepository.deleteAll();
        messageRepository.deleteAll();
        Assert.assertTrue(messageRepository.findAll().isEmpty());
    }
}