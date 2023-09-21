package com.cfex.jdbc.util;

import com.cfexlib.datasource.DynamicDataSource;
import com.cfexlib.datasource.tool.DataSourceManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class BeanContextTest {

    @Test
    void testGetBean() {
        Object dataSource = BeanContextUtil.getBean("dynamicDataSource");
        Object jdbcTemplate = BeanContextUtil.getBean("jdbcTemplate");
        DynamicDataSource bean = BeanContextUtil.getBean(DynamicDataSource.class);
        Object manager = BeanContextUtil.getBean("dataSourceManager");
        DataSourceManager manager2 = BeanContextUtil.getBean(DataSourceManager.class);
        System.out.println("");
    }


    @Test
    void testThreadWithBean() {
        new Thread(new MyThread()).start();
    }
}
