package com.spring.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface RolesRepository extends MongoRepository<Roles, String>{

	public Roles findByNome(String nome);
}