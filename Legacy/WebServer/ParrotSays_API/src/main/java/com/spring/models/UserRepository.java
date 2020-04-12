package com.spring.models;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    User findByUsername(String username);
    
    User findByUsernameAndPassword(String username, String password);
}
