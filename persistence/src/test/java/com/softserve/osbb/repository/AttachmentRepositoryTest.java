package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceAppConfiguration;
import com.softserve.osbb.tenants.model.Attachment;
import com.softserve.osbb.tenants.repository.AttachmentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by nataliia on 10.07.16.
 */

@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceAppConfiguration.class)
@Transactional
public class AttachmentRepositoryTest {

    @Autowired
    AttachmentRepository attachmentRepository;

    private Attachment attachment;
    private Attachment attachment1;

    @Before
    public void init() {

        attachment = new Attachment();
        attachment.setPath("C://...");

        attachment1 = new Attachment();
        attachment1.setPath("D://...");
    }

    @Test
    public void testSave() {
        attachmentRepository.save(attachment);
        assertEquals(attachment, attachmentRepository.findOne(attachment.getAttachmentId()));
    }

    @Test
    public void testSaveList() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment);
        list.add(attachment1);
        attachmentRepository.save(list);
        assertTrue(attachmentRepository.findAll().containsAll(list));
    }

    @Test
    public void testFindOne() {
        attachmentRepository.save(attachment);
        assertEquals(attachment, attachmentRepository.findOne(attachment.getAttachmentId()));
    }

    @Test
    public void testFindAllByIDs() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment1);
        list.add(attachment);
        attachmentRepository.save(list);
        List<Integer> ids = new ArrayList<>();
        ids.add(attachment.getAttachmentId());
        ids.add(attachment1.getAttachmentId());
        assertTrue(attachmentRepository.findAll(ids).containsAll(list));
    }

    @Test
    public void testFindAll() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment);
        list.add(attachment1);
        attachmentRepository.save(attachment);
        attachmentRepository.save(attachment1);
        assertTrue(attachmentRepository.findAll().containsAll(list));
    }

    @Test
    public void testDelete() {
        attachmentRepository.save(attachment);
        attachmentRepository.delete(attachment);
        assertFalse(attachmentRepository.exists(attachment.getAttachmentId()));
    }

    @Test
    public void testDeleteById() {
        attachmentRepository.save(attachment);
        attachmentRepository.delete(attachment.getAttachmentId());
        assertFalse(attachmentRepository.exists(attachment.getAttachmentId()));
    }

    @Test
    public void testDeleteList() {
        List<Attachment> list = new ArrayList<>();
        list.add(attachment);
        list.add(attachment1);
        attachmentRepository.save(list);
        attachmentRepository.delete(list);
        assertFalse(attachmentRepository.exists(attachment.getAttachmentId()));
        assertFalse(attachmentRepository.exists(attachment1.getAttachmentId()));
    }

    @Test
    public void testDeleteAll() {
        attachmentRepository.save(attachment);
        attachmentRepository.save(attachment1);
        attachmentRepository.deleteAll();
        assertTrue(attachmentRepository.findAll().isEmpty());
    }

    @Test
    public void testCount() {
        int before = attachmentRepository.findAll().size();
        attachmentRepository.save(attachment);
        attachmentRepository.save(attachment1);
        assertEquals(before + 2, attachmentRepository.count());
    }

    @Test
    public void testExists() {
        attachmentRepository.save(attachment);
        assertTrue(attachmentRepository.exists(attachment.getAttachmentId()));
    }
}