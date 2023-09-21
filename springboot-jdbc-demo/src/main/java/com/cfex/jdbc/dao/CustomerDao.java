package com.cfex.jdbc.dao;

import com.cfex.jdbc.entity.Customer;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@AllArgsConstructor
public class CustomerDao {
    final JdbcTemplate template;

    public Customer findById(Integer id) {
        String sql = "select * from customer where __pk_customer = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Customer.class), id);
    }
}
