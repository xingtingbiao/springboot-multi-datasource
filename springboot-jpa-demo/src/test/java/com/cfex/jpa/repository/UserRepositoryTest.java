package com.cfex.jpa.repository;

import com.cfexlib.datasource.DataSourceContextHolder;
import com.cfex.jpa.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @Test
    void findById() {
        User user = userRepository.findById(1).orElseGet(null);
        DataSourceContextHolder.setDataSourceKey("t2");
        User user2 = userRepository.findById(1).orElseGet(null);
        DataSourceContextHolder.clearDataSourceKey();
        System.out.println(user);
    }

    @Test
    void testSave() {
        User user = userRepository.save(new User().setName("test1"));
        System.out.println(user);
    }

}
