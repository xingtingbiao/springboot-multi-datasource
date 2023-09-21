package com.cfexlib.datasource.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

public abstract class Properties {

    @Setter
    @Getter
    @Configuration(proxyBeanMethods = false)
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public static class HikariProperties extends Properties {
        private String poolName = "HikariPool-default";

        private Integer maximumPoolSize;

        private Integer minimumIdle;

        public void setPoolName(String poolName) {
            this.poolName = StringUtils.hasText(poolName) ? poolName : "HikariPool-default";
        }
    }
}
