package com.jvinix.iy4s.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jvinix.iy4s.Models.Report;
import com.jvinix.iy4s.Models.ReportRepository;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {

    private ReportRepository reportRepository;

    public ReportViewModel(@NonNull Application application) {
        super(application);
        reportRepository = new ReportRepository(application);
    }

    public void insert(Report report)
    {
        reportRepository.insert(report);
    }

    public void update(Report report)
    {
        reportRepository.update(report);
    }

    public void delete(Report report)
    {
        reportRepository.delete(report);
    }

    public List<Report> getAllReports()
    {
        return reportRepository.getAllReports();
    }

    public Report getReportById(int reportId)
    {
        return reportRepository.getReportById(reportId);
    }

    public List<Report> getReportByStatusCode(int statusCode)
    {
        return reportRepository.getReportByStatusCode(statusCode);
    }

}
