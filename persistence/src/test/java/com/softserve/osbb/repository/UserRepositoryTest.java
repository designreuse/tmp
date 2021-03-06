package com.softserve.osbb.repository;

import com.softserve.osbb.PersistenceAppConfiguration;
import com.softserve.osbb.tenants.model.User;
import com.softserve.osbb.tenants.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by cavayman on 05.07.2016.
 */
@Transactional
@Rollback
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersistenceAppConfiguration.class)
public class UserRepositoryTest extends Assert {
    private User user;
    private User user2;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUpToUserObject() {
       user=new User();
        user.setFirstName("Oleg");
        user.setLastName("Kotsik");
        user.setEmail("cavayman@gmail.com");
        user.setPassword("1111");
        user.setGender("JuniorJavaDev");
        user.setPhoneNumber("+380679167305");
        user.setBirthDate(new java.util.Date(System.currentTimeMillis()));

        user2=new User();
        user2.setFirstName("Oleg");
        user2.setLastName("Kotsik");
        user2.setEmail("fuckthisemail@gmail.com");
        user2.setPassword("1111");
        user2.setGender("JuniorJavaDev");
        user2.setPhoneNumber("+380679167305");
        user2.setBirthDate(new java.util.Date(System.currentTimeMillis()));

    }



    @Test
    public void saveTest() {
        userRepository.save(user);
        userRepository.save(user2);
        assertEquals(user,userRepository.findUserByEmail(user.getEmail()));
        assertEquals(user2,userRepository.findUserByEmail(user2.getEmail()));

    }
    @Test
    public void findByEmailTest() {
        userRepository.save(user);
        userRepository.save(user2);
        assertEquals(user.getEmail(),userRepository.findUserByEmail(user.getEmail()).getEmail());
        assertEquals(user2.getEmail(),userRepository.findUserByEmail(user2.getEmail()).getEmail());
        assertNotEquals(user.getEmail(),userRepository.findUserByEmail(user2.getEmail()).getEmail());


    }
    @After
    public void afterTest(){
    userRepository.deleteAll();
    }
}
