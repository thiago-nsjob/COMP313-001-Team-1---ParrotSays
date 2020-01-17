package com.jvinix.iy4s.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Report {

    @PrimaryKey
    @JsonProperty("ReportId")
    private int ReportId;

    @NonNull
    @JsonProperty("Description")
    private String Description;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @JsonProperty("Picture")
    private byte[] Picture;

    @NonNull
    @JsonProperty("Latitude")
    private double Latitude;

    @NonNull
    @JsonProperty("Longitude")
    private double Longitude;
    @NonNull
    @JsonProperty("DateTimeReport")
    private Long DateTimeReport;

    @JsonProperty("Solution")
    private String Solution;

    @JsonProperty("DateTimeSolution")
    private Long DateTimeSolution;

    @JsonProperty("StatusCode")
    private int StatusCode;

    @JsonProperty("UserId")
    private String UserId;

    @JsonProperty("AdminId")
    private String AdminId;

    public int getReportId() {
        return ReportId;
    }

    public void setReportId(int reportId) {
        ReportId = reportId;
    }

    @NonNull
    public String getDescription() {
        return Description;
    }

    public void setDescription(@NonNull String description) {
        Description = description;
    }

    public byte[] getPicture() {
        return Picture;
    }

    public void setPicture(byte[] picture) {
        Picture = picture;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    @NonNull
    public Long getDateTimeReport() {
        return DateTimeReport;
    }

    public void setDateTimeReport(@NonNull Long dateTimeReport) {
        DateTimeReport = dateTimeReport;
    }

    public String getSolution() {
        return Solution;
    }

    public void setSolution(String solution) {
        Solution = solution;
    }

    public Long getDateTimeSolution() {
        return DateTimeSolution;
    }

    public void setDateTimeSolution(Long dateTimeSolution) {
        DateTimeSolution = dateTimeSolution;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getAdminId() {
        return AdminId;
    }

    public void setAdminId(String adminId) {
        AdminId = adminId;
    }
}
