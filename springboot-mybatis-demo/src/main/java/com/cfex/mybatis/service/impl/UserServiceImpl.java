package com.cfex.mybatis.service.impl;

import com.cfex.mybatis.entity.User;
import com.cfex.mybatis.mapper.UserMapper;
import com.cfex.mybatis.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;

    @Override
    public User findById(Integer id) {

        return userMapper.findById(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int transaction() {
        userMapper.insert(User.of().setName("test1"));
        System.out.println(1/0);
        return 0;
    }
}
