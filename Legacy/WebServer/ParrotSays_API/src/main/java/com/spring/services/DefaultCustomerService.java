package com.spring.services;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.spring.models.Customer;
import com.spring.models.CustomerRepository;

@Service("customerService")
public class DefaultCustomerService implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer login(String username, String password) {
        Optional<Customer> customer = customerRepository.login(username,password);
        System.out.println(customer);
        
        if(customer.isPresent()){
        	Customer custom= customer.get();
        	if(custom.getToken().equals(StringUtils.EMPTY))
        	{
        		String token = UUID.randomUUID().toString();
                custom.setToken(token);
                
                return customerRepository.save(custom);	
        	}
            return custom;
        }
        return null; //StringUtils.EMPTY;
    }

    @Override
    public Optional<User> findByToken(String token) {
        Optional<Customer> customer= customerRepository.findByToken(token);
        if(customer.isPresent()){
            Customer customer1 = customer.get();
            User user= new User(customer1.getUserName(), customer1.getPassword(), true, true, true, true,
                    AuthorityUtils.createAuthorityList("USER"));
            return Optional.of(user);
        }
        return  Optional.empty();
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> customer= customerRepository.findById(id);
        return customer.orElse(null);
    }

	@Override
	public Customer createUser(Customer customer) {
		return customerRepository.save(customer);
	}
}
