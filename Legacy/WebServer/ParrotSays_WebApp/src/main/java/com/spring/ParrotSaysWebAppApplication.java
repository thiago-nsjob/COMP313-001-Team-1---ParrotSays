package com.spring;

/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.spring.models.CustomerService;
import com.spring.models.CustomerServiceImpl;
import com.spring.models.IReportService;
import com.spring.models.ReportServiceImplementation;

@SpringBootApplication
public class ParrotSaysWebAppApplication {

	public static final String SERVER_URL = "http://localhost:8167";
	
	public static void main(String[] args) {
		SpringApplication.run(ParrotSaysWebAppApplication.class, args);
		
		System.out.println("WebApp Server started...");
	}
	
	@Bean
	public RestTemplate restTemplate()
	{
		return new RestTemplate();
	}
	
	@Bean
	public IReportService reportService()
	{
		return new ReportServiceImplementation(SERVER_URL);
	}
	
	@Bean
	public CustomerService customerService()
	{
		return new CustomerServiceImpl(SERVER_URL);
	}

}
