package com.cfex.jdbc;

import com.cfexlib.datasource.config.jdbc.EnableJdbcMultiDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, SqlInitializationAutoConfiguration.class})
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
@EnableJdbcMultiDataSource
@EnableFeignClients
public class SpringbootJdbcDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJdbcDemoApplication.class, args);
	}

}
