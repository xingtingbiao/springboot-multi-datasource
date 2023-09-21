package com.cfex.jdbc.controller;

import com.cfex.jdbc.dao.CustomerDao;
import com.cfex.jdbc.entity.Customer;
import com.cfexlib.datasource.route.DataSourceRouter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "customer")
@Slf4j
@AllArgsConstructor
@DataSourceRouter
public class CustomerController {
    private final CustomerDao customerDao;

    @GetMapping(value = "/multi/tenant/{id}/{marketplaceCode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Customer multiTenant(@PathVariable Integer id, @PathVariable String marketplaceCode) {
        return customerDao.findById(id);
    }

}
