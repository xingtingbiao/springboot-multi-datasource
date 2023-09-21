package com.cfex.jpa.service;

import com.cfex.jpa.entity.User;

public interface IUserService {
    User findById(Integer id);

    int transaction();
}
