package com.spring.models;

/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class ReportServiceImplementation implements IReportService {
	
	@Autowired
	protected RestTemplate restTemplate;
	
	protected String serverUrl;
	
	public ReportServiceImplementation(String serverUrl)
	{
		this.serverUrl = serverUrl.startsWith("http") ? serverUrl: "http://"+serverUrl;
	}
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Override
	public List<Report> getAllReports(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);
		
		ResponseEntity<Report[]> re = restTemplate.exchange(serverUrl+"/api/reports/getall", HttpMethod.GET, entity, Report[].class);
		return Arrays.asList(re.getBody());
	}

	@Override
	public Report getReportById(int reportId, String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer "+token);
		
		HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);
		
		ResponseEntity<Report> re = restTemplate.exchange(serverUrl+"/api/reports/getreport/"+reportId, HttpMethod.GET, entity, Report.class);
		
		return re.getBody();
	}

	@Override
	public boolean insertReport(Report report, String token) {
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer "+token);
			
			HttpEntity<Report> entity = new HttpEntity<Report>(report,headers);
			
			restTemplate.postForObject(serverUrl+"/api/reports/", entity, Report.class);
			return true;
		}
		catch(Exception exc)
		{
			System.out.println("Error: "+exc.getMessage());
			return false;
		}
	}

	@Override
	public boolean updateReport(Report report, String token) {
		
		System.out.println(report);
		
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer "+token);
			
			HttpEntity<Report> entity = new HttpEntity<Report>(report,headers);
			
			restTemplate.put(serverUrl+"/api/reports/updatereport/"+report.getReportId(), entity);
			return true;
		}
		catch(Exception exc)
		{
			System.out.println("Error: "+exc.getMessage());
			return false;
		}
	}

	@Override
	public boolean deleteReport(int reportId, String token) {
		try
		{
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer "+token);
			
			HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);
			
			restTemplate.exchange(serverUrl+"/api/reports/delreport/"+reportId, HttpMethod.DELETE, entity, Report.class);
			
			//restTemplate.delete(serverUrl+"/api/reports/"+reportId);
			return true;
		}
		catch(Exception exc)
		{
			System.out.println("Error: "+exc.getMessage());
			return false;
		}
	}

}
