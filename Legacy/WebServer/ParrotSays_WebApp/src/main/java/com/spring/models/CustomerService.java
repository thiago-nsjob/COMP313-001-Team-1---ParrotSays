package com.spring.models;

public interface CustomerService {
	
	String Login(Customer customer);
	boolean CreateUser(Customer customer, String token);

}
