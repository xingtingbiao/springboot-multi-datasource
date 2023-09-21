package com.cfex.mybatis;

import com.cfexlib.datasource.config.mybatis.EnableMybatisMultiDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.cfex.mybatis.mapper")
@EnableMybatisMultiDataSource
public class SpringbootMybatisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMybatisApplication.class, args);
	}

}
