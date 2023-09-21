package com.cfex.jdbc.datasource;

import com.cfexlib.datasource.config.BaseDataSourceConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataSourceTest {
//    @Autowired private DataSourceProperties dataSourceProperties;
//    @Autowired private JdbcProperties jdbcProperties;

//    @Autowired
//    private BaseDataSourceConfig baseDataSourceConfig;

//    @Autowired private HibernateJpaAutoConfiguration jpaAutoConfiguration;

    @Test
    void test() {
//        DataSource d1 = baseDataSourceConfig.dynamicDataSource();
//        DataSource d2 = baseDataSourceConfig.dynamicDataSource();
        System.out.println();
    }


}
