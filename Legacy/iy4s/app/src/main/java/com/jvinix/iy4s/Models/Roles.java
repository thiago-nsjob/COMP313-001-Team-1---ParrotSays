package com.jvinix.iy4s.Models;

public class Roles {
 
    private Long id;
    private String authority;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}
}
