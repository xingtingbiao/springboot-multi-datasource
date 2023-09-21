package com.cfex.jpa.repository;

import com.cfex.jpa.entity.Customer;
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
public class CustomerRepositoryTest {
    @Autowired private CustomerRepository customerRepository;
    @Autowired private DataSourceManager dataSourceManager;

    @Test
    void findById() {
        dataSourceManager.routing("CU");
        Customer customer = customerRepository.findById(1l).orElseGet(null);
        System.out.println(customer);
    }


}
