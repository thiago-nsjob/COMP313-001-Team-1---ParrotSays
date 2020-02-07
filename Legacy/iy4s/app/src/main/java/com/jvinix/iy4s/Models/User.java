package com.jvinix.iy4s.Models;

//import com.fasterxml.jackson.annotation.JsonIdentityInfo;
//import com.fasterxml.jackson.annotation.ObjectIdGenerators;


import java.util.ArrayList;
import java.util.List;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
public class User {
    
	//private static final long serialVersionUID = 1L;

    //private Long id;

    private String username;
    private String password;
    private String email;

    //private List<Roles> roles = new ArrayList<Roles>();

//    public List<Roles> getRoles() {
//		return roles;
//	}
    
//	public void setRoles(List<Roles> roles) {
//		this.roles = roles;
//	}
	
//	public boolean setSingleRole(Roles role)
//	{
//		if(!roles.contains(role))
//		{
//			roles.add(role);
//			return true;
//		}
//		else
//		{
//			return false;
//		}
//	}

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

    public String getUsername() {
        return username;
    }

//	@Override
//	public String toString() {
//		return "User [username=" + username + ", email=" + email + ", roles=" + roles + "]";
//	}
    
    
}
