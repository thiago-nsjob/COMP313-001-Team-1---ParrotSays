package com.spring.models;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Integer>{
	
	public List<Report> findByStatusCode(int statusCode);

}
