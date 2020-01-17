package com.spring.services;

import com.spring.models.Customer;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;

public interface CustomerService {

    Customer login(String username, String password);
    Optional<User> findByToken(String token);
    Customer findById(Long id);
    Customer createUser(Customer customer);
}
