package com.spring.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.spring.models.Customer;
import com.spring.services.CustomerService;

@RestController
//@RequestMapping("/api/users")
public class UserProfileController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(value = "/api/users/user/{id}",produces = "application/json")
    public Customer getUserDetail(@PathVariable Long id){
        return customerService.findById(id);
    }
    
 // Create a new user
    @PostMapping("/api/users/createuser")
    public Customer createUser(@Valid @RequestBody Customer customer) {
        return customerService.createUser(customer);
    }
    
    
}
