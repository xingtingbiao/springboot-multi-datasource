package com.cfex.test.service;

import com.cfex.test.entity.Tom;
import com.cfex.test.entity.User;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(name = "my.test")
@Data
public class TestService {
    private String name = "tom";

    @Bean
    public Tom tom() {
        return Tom.of().setName("tom");
    }

    @Bean
    public User user(Tom tom) {
        return User.of().setName(tom.getName());
    }
}
