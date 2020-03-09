package com.spring.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	protected RestTemplate restTemplate;
	
	protected String serverUrl;
	
	public CustomerServiceImpl(String serverUrl)
	{
		this.serverUrl = serverUrl.startsWith("http") ? serverUrl: "http://"+serverUrl;
	}

	@Override
	public boolean CreateUser(Customer customer, String token) {
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer "+token);
			
			customer.setToken("");
			
			HttpEntity<Customer> entity = new HttpEntity<Customer>(customer,headers);
			
			restTemplate.postForObject(serverUrl+"/api/users/createuser/", entity, Customer.class);
			return true;
		}
		catch(Exception exc)
		{
			System.out.println("Error: "+exc.getMessage());
			return false;
		}
	}
	
	@Override
	public String Login(Customer customer) {
		try
		{
			return restTemplate.postForEntity(serverUrl+"/api/users/login", customer, Customer.class).getBody().getToken();
		}
		catch(Exception exc)
		{
			System.out.println("Error: "+exc.getMessage());
			return "";
		}
	}

}
