package com.spring.controllers;


/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import java.util.List;
import javax.validation.Valid;
import javassist.NotFoundException;

import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.spring.models.*;


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
    public Report addReport(@Valid @RequestBody Report report) {
        return repo.save(report);
    }
    
    @Secured({ "ROLE_SECGUARD", "ROLE_ADMIN" })
    // Get a Single report
    @GetMapping("/getreport/{id}")
    public Report getReportById(@PathVariable(value = "id") Integer reportId) throws NotFoundException  {
        return repo.findById(reportId)
        		.orElseThrow(() -> new NotFoundException("ReportId "+ reportId+ " Not found."));
    }

    // Update a Report
    @Secured({ "ROLE_SECGUARD", "ROLE_ADMIN" })
    @PutMapping("/updatereport/{id}")
    public Report updateReport(@PathVariable(value = "id") Integer reportId,
                           @Valid @RequestBody Report reportEdited) throws NotFoundException {

    	Report reportTemp = repo.findById(reportId)
    			.orElseThrow(() -> new NotFoundException("ReportId "+ reportId+ " Not found."));
		
    	reportTemp.setAdminId(reportEdited.getAdminId());
    	reportTemp.setDateTimeSolution(reportEdited.getDateTimeSolution());
    	reportTemp.setSolution(reportEdited.getSolution());
    	reportTemp.setStatusCode(reportEdited.getStatusCode());
		
		return repo.save(reportTemp);
    }

    // Delete a Report
    @Secured({ "ROLE_ADMIN" })
    @DeleteMapping("/delreport/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable(value = "id") Integer reportId) throws NotFoundException {

		repo.deleteById(reportId);//.delete(report);
		
		return ResponseEntity.ok().build();
    }
}
