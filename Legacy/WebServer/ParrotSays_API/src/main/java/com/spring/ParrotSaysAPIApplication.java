package com.spring;

/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParrotSaysAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParrotSaysAPIApplication.class, args);
		
		System.out.println("REST Web service started...");
	}

}
