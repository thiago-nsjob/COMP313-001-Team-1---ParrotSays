package com.spring.controllers;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.spring.models.Customer;
import com.spring.services.CustomerService;

@RestController
public class TokenController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/token")
    public Customer getToken(@Valid @RequestBody Customer customer){
    	System.out.println("TokenController: " + customer);
    	try{
    		return customerService.login(customer.getUserName(), customer.getPassword());
    	}
    	catch(Exception exc)
    	{
    		return null;
    	}
//       if(StringUtils.isEmpty(token)){
//           return null;//"No token found";
//       }
//       return token;
    }
}
