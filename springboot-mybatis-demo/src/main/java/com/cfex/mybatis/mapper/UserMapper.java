package com.cfex.mybatis.mapper;

import com.cfex.mybatis.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {
    User findById(@Param("id") Integer id);

    int insert(User user);
}
