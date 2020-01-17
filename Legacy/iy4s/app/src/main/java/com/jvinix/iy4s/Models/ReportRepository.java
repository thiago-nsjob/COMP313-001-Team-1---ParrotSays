package com.jvinix.iy4s.Models;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ReportRepository {

    private ReportDao reportDao;

    public  ReportRepository (Context context)
    {
        AppDatabase db = AppDatabase.getInstance(context);
        reportDao = db.reportDao();
    }

    public void insert (final Report report)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                reportDao.insert(report);
            }
        }).start();
    }

    public void update (final Report report)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                reportDao.update(report);
            }
        }).start();
    }

    public void delete (final Report report)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                reportDao.delete(report);
            }
        }).start();
    }

    public List<Report> getAllReports()
    {
        try {
            return new getAllReportsAsync().execute().get();
        } catch (Exception e) {
            Log.e("ERROR: ", e.getMessage());
            return null;
        }
    }

    private class getAllReportsAsync extends AsyncTask<Void, Void, List<Report>>
    {
        @Override
        protected List<Report> doInBackground(Void... voids) {
            return reportDao.getAllReports();
        }
    }

    public Report getReportById(int reportId)
    {
        try {
            return new getReportByIdAsync().execute(reportId).get();
        } catch (Exception e) {
            Log.e("ERROR: ", e.getMessage());
            return null;
        }
    }

    private class getReportByIdAsync extends AsyncTask<Integer, Void, Report>
    {
        @Override
        protected Report doInBackground(Integer... params) {
            return reportDao.getReportById(params[0]);
        }
    }

    public List<Report> getReportByStatusCode(int statusCode)
    {
        try {
            return new getReportByStatusCodeAsync().execute(statusCode).get();
        } catch (Exception e) {
            Log.e("ERROR: ", e.getMessage());
            return null;
        }
    }

    private class getReportByStatusCodeAsync extends AsyncTask<Integer, Void, List<Report>>
    {
        @Override
        protected List<Report> doInBackground(Integer... params) {
            return reportDao.getReportByStatusCode(params[0]);
        }
    }

}
