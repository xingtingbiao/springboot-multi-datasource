package com.cfexlib.datasource.config.jdbc;

import com.cfexlib.datasource.config.BaseDataSourceConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration/*(proxyBeanMethods = false)*/
@EnableTransactionManagement
@Import(BaseDataSourceConfig.class)
class JdbcDataSourceConfig {
    @Resource(name = "dynamicDataSource")
    private DataSource dynamicDataSource;

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(this.dynamicDataSource);
    }

}
