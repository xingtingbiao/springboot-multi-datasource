package com.cfex.jdbc.service;

import com.cfexlib.datasource.DataSourceContextHolder;
import com.cfex.jdbc.entity.User;
import com.cfex.jdbc.service.impl.ServiceA;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired private IUserService userService;
    @Autowired private ServiceA serviceA;

    @Autowired private OpenfeignTest openfeignTest;

    @Test
    void testFindById() {
        User byId = userService.findById(1);
        DataSourceContextHolder.setDataSourceKey("t2");
        User user2 = userService.findById(1);
        DataSourceContextHolder.clearDataSourceKey();
        Assert.assertNotNull(byId);
    }

//    @Test
//    void testTransaction() {
//        userService.transaction();
//    }

    @Test
    @DisplayName("Nesting problem")
    void testTransactionSameClass() {
        serviceA.methodA();
    }

    @Test
    void testOpenfeignTimeout() {
        long start = System.currentTimeMillis();
        try {
            String res = openfeignTest.testTimeOut();
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
            long end = System.currentTimeMillis();
            System.out.println(end - start);
        }


    }
}
