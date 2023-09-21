package com.cfex.jdbc.service.impl;

import com.cfex.jdbc.dao.UserDao;
import com.cfex.jdbc.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ServiceB {
    private final UserDao userDao;

    @Transactional
    public int methodB() {
        userDao.insert(User.of().setName("order"));
        System.out.println(1/0);
        userDao.insert(User.of().setName("product"));
        return 0;
    }
}
