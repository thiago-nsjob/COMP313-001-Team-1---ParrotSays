package com.spring.models;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportRepository extends MongoRepository<Report, String>{
	
	//public List<Report> findByStatusCode(int statusCode);
	
	@Query("SELECT new Report(r.reportId, r.description, r.latitude, r.longitude, r.dateTimeReport, r.statusCode) FROM Report r")
    public List<Report> findAllReports();
	
	@Query("SELECT new Report(r.reportId, r.description, r.latitude, r.longitude, r.dateTimeReport, r.statusCode) FROM Report r WHERE statusCode = :statusCode")
    public List<Report> findByStatusCode(@Param("statusCode") int statusCode);

}
