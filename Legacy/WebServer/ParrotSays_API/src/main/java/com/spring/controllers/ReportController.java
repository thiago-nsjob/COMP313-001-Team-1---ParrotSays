package com.spring.controllers;

import java.util.ArrayList;

/* 
301016383 - Julio Vinicius A. de Carvalho
November 17, 2019
*/

import java.util.List;
import javax.validation.Valid;
import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import com.spring.models.*;


@RestController
@RequestMapping("/api")
public class ReportController {
	
	@Autowired
	ReportRepository repo;
	
	// Get all reports
	@GetMapping("/reports")
	public List<Report> getAllReports()
	{
		return repo.findAll();
	}
	
	// Get all reports
	@GetMapping("/reports/bystatus/{id}")
	public List<Report> getReportsByStatus (@PathVariable(value = "id") Integer reportId)
	{
		List<Report> reportList = new ArrayList<Report>();
		for(Report rep : reportList)
		{
			if(rep.getStatusCode() == 1)
			{
				reportList.add(rep);
			}
		}
		return reportList;
	}
	
	// Create a new report
    @PostMapping("/reports")
    public Report addReport(@Valid @RequestBody Report report) {
    	System.out.println("ReportController: " + report);
        return repo.save(report);
    }
    
    // Get a Single report
    @GetMapping("/reports/{id}")
    public Report getReportById(@PathVariable(value = "id") Integer reportId) throws NotFoundException  {
        return repo.findById(reportId)
        		.orElseThrow(() -> new NotFoundException("ReportId "+ reportId+ " Not found."));
    }

    // Update a Report
    @PutMapping("/reports/{id}")
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
    @DeleteMapping("/reports/{id}")
    public ResponseEntity<?> deleteReport(@PathVariable(value = "id") Integer reportId) throws NotFoundException {
//        Report report = repo.findById(reportId)
//        		.orElseThrow(() -> new NotFoundException("ReportId "+ reportId+ " Not found."));

		repo.deleteById(reportId);//.delete(report);
		
		return ResponseEntity.ok().build();
    }
}
