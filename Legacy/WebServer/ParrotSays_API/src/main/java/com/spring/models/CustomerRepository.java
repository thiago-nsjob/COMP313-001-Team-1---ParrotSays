package com.spring.models;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Query(value = "SELECT u FROM Customer u where u.userName = ?1 and u.password = ?2 ")
    Optional<Customer> login(String username,String password);

    Optional<Customer> findByToken(String token);
}
