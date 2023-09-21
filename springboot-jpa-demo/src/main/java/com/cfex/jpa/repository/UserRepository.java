package com.cfex.jpa.repository;

import com.cfex.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Override
    Optional<User> findById(Integer integer);

    @Override
    @SuppressWarnings("unchecked")
    User save(User user);
}
