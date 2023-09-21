package com.cfex.mybatis.mapper;

import com.cfexlib.datasource.DataSourceContextHolder;
import com.cfex.mybatis.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired private UserMapper userMapper;

    @Test
    void findById() {
        User user = userMapper.findById(1);
        DataSourceContextHolder.setDataSourceKey("t2");
        User user2 = userMapper.findById(1);
        DataSourceContextHolder.clearDataSourceKey();
        System.out.println(user);
    }


}
