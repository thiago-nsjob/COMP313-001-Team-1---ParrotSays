package com.spring.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
public class User implements UserDetails {
    
	private static final long serialVersionUID = 1L;

	@Id
    private String id;

	@Indexed(unique=true)
    private String username;
    private String password;
    private String email;

    @DBRef
    private List<Roles> roles = new ArrayList<Roles>();

    public List<Roles> getRoles() {
		return roles;
	}
    
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	
	public boolean setSingleRole(Roles role)
	{
		if(!roles.contains(role))
		{
			roles.add(role);
			return true;
		}
		else
		{
			return false;
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
		this.password = password;
	}

	@Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

	@Override
	public String toString() {
		return "User [username=" + username + ", email=" + email + ", roles=" + roles + "]";
	}
    
    
}
