package com.cfex.mybatis.service;

import com.cfex.mybatis.entity.User;

public interface IUserService {
    User findById(Integer id);

    int transaction();
}
