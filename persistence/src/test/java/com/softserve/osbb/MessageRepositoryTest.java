package com.softserve.osbb;

import com.softserve.osbb.dao.MessageRepository;
import com.softserve.osbb.model.MessageEntity;
import com.softserve.osbb.model.UserEntity;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;


/**
 * Created by Kris on 05.07.2016.
 */
public class MessageRepositoryTest {

    private MessageEntity messageEntity = new MessageEntity();

    private UserEntity userEntity = new UserEntity();
    @Autowired
    MessageRepository messageRepository;

    @Test

    public void testFindAll(){
        Assert.assertNull(messageEntity.getMessageId());
        userEntity.setBirthdate(new Date());
        userEntity.setEmail("blabla@gmail.com");
        userEntity.setFirstname("Vanya");
        userEntity.setLastname("Popov");
        userEntity.setGender("man");

        messageEntity.setUsers(userEntity);
        messageEntity.setMessage("Hi! This is a first message in our database.");
        messageEntity.setDescription("some description");
        messageEntity.setTime("some date");

        messageRepository.save(messageEntity);
        Assert.assertNotNull(messageEntity.getMessageId());

    }
}