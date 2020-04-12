package com.spring.controllers;


import java.io.IOException;

/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.spring.models.*;
import com.spring.security.jwt.ServletUtil;


@RestController
@RequestMapping("/api/reports")
public class ReportController {
	
	@Autowired
	ReportRepository repo;
	
	// Get all reports
	@Secured({ "ROLE_ADMIN" })
	@GetMapping("/getall")
	public List<Report> getAllReports()
	{
		return repo.findAllReports();//findAll();
	}
	
	@Secured({ "ROLE_SECGUARD", "ROLE_ADMIN" })
	// Get all reports by status
	@GetMapping("/getbystatus/{id}")
	public List<Report> getReportsByStatus (@PathVariable(value = "id") Integer statusCode)
	{
		return repo.findByStatusCode(statusCode);
	}
	
	// Create a new report
    @PostMapping("/addreport")
    public void addReport(@Valid @RequestBody Report report, HttpServletResponse response) throws Exception {
        try
        {
        	String json = ServletUtil.getJson("reportId", repo.save(report).getReportId());
        	ServletUtil.write(response, HttpStatus.OK, json);
        }
        catch(Exception exc)
        {
        	ServletUtil.write(response, HttpStatus.BAD_REQUEST, ServletUtil.getJson("error", exc.getCause().toString()));
        }
    }
    
    // Get a Single report
    @GetMapping("/getreport/{id}")
    public Report getReportById(@PathVariable(value = "id") String reportId, HttpServletResponse response) throws Exception  
    {
        return repo.findById(reportId)
        		.orElseThrow(() -> new Exception("ReportId "+ reportId+ " Not found."));
    }

    // Update a Report
    @Secured({ "ROLE_SECGUARD", "ROLE_ADMIN" })
    @PutMapping("/updatereport/{id}")
    public Report updateReport(@PathVariable(value = "id") String reportId,
                           @RequestBody Report reportEdited) throws Exception {

    	Report reportTemp = repo.findById(reportId)
    			.orElseThrow(() -> new Exception("ReportId "+ reportId+ " Not found."));
		
    	reportTemp.setAdminId(reportEdited.getAdminId());
    	reportTemp.setDateTimeSolution(reportEdited.getDateTimeSolution());
    	reportTemp.setSolution(reportEdited.getSolution());
    	reportTemp.setStatusCode(reportEdited.getStatusCode());
		
		return repo.save(reportTemp);
    }

    // Delete a Report
    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/delreport/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable(value = "id") String reportId) throws Exception {

		repo.deleteById(reportId);//.delete(report);
		
		return ResponseEntity.ok().build();
    }
}
