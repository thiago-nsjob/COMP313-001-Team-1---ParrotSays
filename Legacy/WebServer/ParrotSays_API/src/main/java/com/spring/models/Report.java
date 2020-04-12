package com.spring.models;

import java.util.Arrays;

//import javax.persistence.Column;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

@Document
public class Report {

	@Id
	private String reportId;
	@NotEmpty
    private String description;
	
	//@Column(columnDefinition = "TEXT")
    private byte[] picture;
	@NonNull
    private double latitude;
	@NonNull
    private double longitude;
	@NonNull
    private Long dateTimeReport;
    private String solution;
    private Long dateTimeSolution;
    @NonNull
    private int statusCode;
    private String userId;
    private String adminId;
    
    public Report() {}
    
	public Report(String reportId, String description, double latitude, double longitude, Long dateTimeReport,
			int statusCode) {
		super();
		this.reportId = reportId;
		this.description = description;
		this.latitude = latitude;
		this.longitude = longitude;
		this.dateTimeReport = dateTimeReport;
		this.statusCode = statusCode;
	}

	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public byte[] getPicture() {
		return picture;
	}
	public void setPicture(byte[] picture) {
		this.picture = picture;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Long getDateTimeReport() {
		return dateTimeReport;
	}
	public void setDateTimeReport(Long dateTimeReport) {
		this.dateTimeReport = dateTimeReport;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	public Long getDateTimeSolution() {
		return dateTimeSolution;
	}
	public void setDateTimeSolution(Long dateTimeSolution) {
		this.dateTimeSolution = dateTimeSolution;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	@Override
	public String toString() {
		return "Report [reportId=" + reportId + ", description=" + description + ", picture=" + Arrays.toString(picture)
				+ ", latitude=" + latitude + ", longitude=" + longitude + ", dateTimeReport=" + dateTimeReport
				+ ", solution=" + solution + ", dateTimeSolution=" + dateTimeSolution + ", statusCode=" + statusCode
				+ ", userId=" + userId + ", adminId=" + adminId + "]";
	}
    
	
    
}
