package com.spring.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Document
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id" )
public class Roles implements GrantedAuthority {
 
	private static final long serialVersionUID = 1L;

	@Id
    private String id;
    private String nome;
    
    @DBRef
    List<User> users;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
    public String getAuthority() {
        return nome;
    }

	@Override
	public String toString() {
		return "Roles [id=" + id + ", nome=" + nome + "]";
	}
	
}
