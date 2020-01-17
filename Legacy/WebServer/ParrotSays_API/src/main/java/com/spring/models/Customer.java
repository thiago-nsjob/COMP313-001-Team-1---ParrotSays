package com.spring.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue
    private Long id;
    private String userName;
    private String password;
    private String token;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }


    @Override
	public String toString() {
		return "Customer [id=" + id + ", userName=" + userName + ", password=" + password + ", token=" + token + "]";
	}

	public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
