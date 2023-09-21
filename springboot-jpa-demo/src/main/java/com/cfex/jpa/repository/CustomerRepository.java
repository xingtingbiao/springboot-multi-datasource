package com.cfex.jpa.repository;

import com.cfex.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Override
    Optional<Customer> findById(Long id);

    @Override
    @SuppressWarnings("unchecked")
    Customer save(Customer customer);
}
