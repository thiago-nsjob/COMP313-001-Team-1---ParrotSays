package com.jvinix.iy4s.Models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
//import com.jvinix.iy4s.Utils.Converter;

@Entity
public class Report {

    @PrimaryKey
    @SerializedName("reportId")
    private int ReportId;

    @NonNull
    @SerializedName("description")
    private String Description;

    //@ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    @SerializedName("picture")
    //private byte[] Picture;
    private String Picture;

    @NonNull
    @SerializedName("latitude")
    private double Latitude;

    @NonNull
    @SerializedName("longitude")
    private double Longitude;

    @NonNull
    @SerializedName("dateTimeReport")
    private Long DateTimeReport;

    @SerializedName("solution")
    private String Solution;

    @SerializedName("dateTimeSolution")
    private Long DateTimeSolution;

    @SerializedName("statusCode")
    private int StatusCode;

    @SerializedName("userId")
    private String UserId;

    @SerializedName("adminId")
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

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
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

    @Override
    public String toString() {
        return "Report{" +
                "ReportId=" + ReportId +
                ", Description='" + Description + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", DateTimeReport=" + DateTimeReport +
                ", Solution='" + Solution + '\'' +
                ", DateTimeSolution=" + DateTimeSolution +
                ", StatusCode=" + StatusCode +
                ", UserId='" + UserId + '\'' +
                ", AdminId='" + AdminId + '\'' +
                '}';
    }
}
