package com.spring.models;

import javax.validation.constraints.NotEmpty;

public class Login {
	
	@NotEmpty
	private String username;
	
	private String email;
	
	@NotEmpty
    private String password;
    
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
