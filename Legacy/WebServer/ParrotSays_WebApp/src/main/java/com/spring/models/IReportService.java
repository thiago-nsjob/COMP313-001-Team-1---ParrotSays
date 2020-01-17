package com.spring.models;

/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import java.util.List;

public interface IReportService {

	List<Report> getAllReports(String token);
	Report getReportById(int patientId, String token);
	boolean insertReport(Report patient, String token);
	boolean updateReport(Report patient, String token);
	boolean deleteReport(int patientId, String token);
	
}
