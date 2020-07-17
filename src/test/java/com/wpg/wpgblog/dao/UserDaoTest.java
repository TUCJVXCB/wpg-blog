package com.wpg.wpgblog.dao;

import com.wpg.wpgblog.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    void findByUsername() {
        System.out.println(userDao.findByUsername("admin"));
    }

    @Test
    void findByE() {
        User user = new User();
        user.setEmail("213");
        user.setPassword("132");
        user.setNickname("312");

        userDao.addUser(user);
    }
}
