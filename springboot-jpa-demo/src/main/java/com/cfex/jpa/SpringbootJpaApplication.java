package com.cfex.jpa;

import com.cfexlib.datasource.config.jpa.EnableJpaMultiDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.cfex.jpa.repository")
@SpringBootApplication
@EnableJpaMultiDataSource
public class SpringbootJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

}
