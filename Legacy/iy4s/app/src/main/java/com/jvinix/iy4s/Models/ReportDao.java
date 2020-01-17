package com.jvinix.iy4s.Models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ReportDao {

    @Insert//(onConflict = OnConflictStrategy.REPLACE)
    void insert (Report report);

    @Update
    void update (Report report);

    @Delete
    void delete (Report report);

    @Query("select * from Report")
    List<Report> getAllReports();

    @Query("select * from Report where reportId = :reportId")
    Report getReportById(int reportId);

    @Query("select * from Report where StatusCode = :statusCode")
    List<Report> getReportByStatusCode(int statusCode);
}