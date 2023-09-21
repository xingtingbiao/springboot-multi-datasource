package com.cfex.jdbc.service;


import com.cfex.jdbc.entity.User;

public interface IUserService {
    User findById(Integer id);

    int transaction();
}
