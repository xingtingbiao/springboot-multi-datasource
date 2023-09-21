package com.cfex.jpa.service.impl;

import com.cfex.jpa.entity.User;
import com.cfex.jpa.repository.UserRepository;
import com.cfex.jpa.service.IUserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {
    private final UserRepository userRepository;
    @Override
    public User findById(Integer id) {
        return userRepository.findById(1).orElseGet(null);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int transaction() {
        userRepository.save(new User().setName("test1"));
        System.out.println(1/0);
        userRepository.save(new User().setName("test2"));
        return 0;
    }
}
