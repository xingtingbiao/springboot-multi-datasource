package com.cfex.test.datasource;

import com.cfex.test.entity.Tom;
import com.cfex.test.entity.User;
import com.cfex.test.service.TestService;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {
    @Autowired private DataSourceProperties dataSourceProperties;
    @Autowired private TestService testService;

    @Autowired private User user;

    @Autowired private Tom tom;

    @Autowired private ApplicationContext context;

    @Autowired private JdbcTemplate template;

//    @Autowired private HikariConfig hikariConfig;

    @Autowired private HikariDataSource dataSource;

    @Test
    void test() {
//        User user1 = testService.user();
//        User user2 = testService.user();
//        User bean = context.getBean(User.class);
        System.out.println();
    }


}
