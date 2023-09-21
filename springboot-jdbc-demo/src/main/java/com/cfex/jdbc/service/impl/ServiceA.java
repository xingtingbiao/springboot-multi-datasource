package com.cfex.jdbc.service.impl;

import com.cfex.jdbc.dao.UserDao;
import com.cfex.jdbc.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class ServiceA {
    private final UserDao userDao;
    private final ServiceB serviceB;

    public int methodA() {
//        return methodB();
//        return serviceB.methodB();
        try {
            return serviceB.methodB();
        } catch (Exception e) {
            log.error("", e);
        }
        return 0;
    }

    @Transactional
    public int methodB() {
        userDao.insert(User.of().setName("order"));
        System.out.println(1/0);
        userDao.insert(User.of().setName("product"));
        return 0;
    }
}
