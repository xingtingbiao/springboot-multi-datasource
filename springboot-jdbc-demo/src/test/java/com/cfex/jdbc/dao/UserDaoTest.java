package com.cfex.jdbc.dao;

import com.cfex.jdbc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {
    @Autowired private UserDao userDao;

    @Test
    void testFindById() {
        User byId = userDao.findById(1);
        Assert.assertNotNull(byId);
    }

    @Test
    void testInsert() {
        userDao.insert(User.of().setName("test"));
    }

    @Test
    void testInsertBatch() {
        List<User> users = List.of(User.of().setName("b1"), User.of().setName("b2"), User.of().setName("b3"), User.of().setName("b4"));
        int i = userDao.insertBatch(users);
        System.out.println();
    }
}
