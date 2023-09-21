package com.cfex.jdbc.service.impl;

import com.cfex.jdbc.dao.UserDao;
import com.cfex.jdbc.entity.User;
import com.cfex.jdbc.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserDao userDao;

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional
    public int transaction() {
        userDao.insert(User.of().setName("test1"));
        System.out.println(1/0);
        userDao.insert(User.of().setName("test2"));
        return 0;
    }
}
