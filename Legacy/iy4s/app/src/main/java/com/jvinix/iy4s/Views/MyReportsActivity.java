package com.jvinix.iy4s.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jvinix.iy4s.Models.Customer;
import com.jvinix.iy4s.Models.Report;
import com.jvinix.iy4s.R;
import com.jvinix.iy4s.Utils.Converter;
import com.jvinix.iy4s.ViewModels.ReportViewModel;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyReportsActivity extends AppCompatActivity {

    private String ServerUrl;
    private String Token;
    private ReportViewModel rvm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports);

        setTitle("List of Reports");

        SharedPreferences myPreference = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        ServerUrl = myPreference.getString("IPAddress", "");
        Token = myPreference.getString("Token", "");
        int statuscode = getIntent().getIntExtra("statuscode", 0);

        rvm = ViewModelProviders.of(this).get(ReportViewModel.class);

        List<Report> reportList = new ArrayList<Report>();

        try {
            if (statuscode == 1)
            {
                //reportList = new RestTaskGetAllByStatusCode().execute(statuscode).get();
                reportList = rvm.getAllReports();
            }
            else {
                reportList = rvm.getAllReports();
                //reportList = new RestTaskGetAll().execute().get();;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        final List<Report> finalList = reportList;

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, finalList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text1 = (TextView) view.findViewById(android.R.id.text1);
                TextView text2 = (TextView) view.findViewById(android.R.id.text2);

                text1.setText(finalList.get(position).getReportId()+" - "+finalList.get(position).getStatusCode()+" - "+ new Date(finalList.get(position).getDateTimeReport()));
                text1.setTextSize(16);
                text2.setText("Description: "+finalList.get(position).getDescription());
                return view;
            }
        };

        ListView listView = (ListView) findViewById(R.id.lvReports);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Intent i =  new Intent(this, Detaisl)
                //reportList.get(position).getReportId();

            }});
    }

    class RestTaskGetAll extends AsyncTask<Void, Void, List<Report>> {

        @Override
        protected List<Report> doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer "+Token);

                HttpEntity<String> entity = new HttpEntity<String>("parameters",headers);

                ResponseEntity<Report[]> re = restTemplate.exchange(ServerUrl+"/api/reports/", HttpMethod.GET, entity, Report[].class);
                return Arrays.asList(re.getBody());

            }
            catch(Exception ex)
            {
                Log.e("ERROR: ", ex.getMessage());
                return null;
            }
        }
    }

    class RestTaskGetAllByStatusCode extends AsyncTask<Integer, Void, List<Report>> {

        @Override
        protected List<Report> doInBackground(Integer... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer "+Token);

                HttpEntity<String> entity = new HttpEntity<String>("tokens",headers);

                ResponseEntity<Report[]> re = restTemplate.exchange(ServerUrl+"/api/reports/bystatus/"+params[0].toString(), HttpMethod.GET, entity, Report[].class);
                return Arrays.asList(re.getBody());

            }
            catch(Exception ex)
            {
                Log.e("ERROR: ", ex.getMessage());
                return null;
            }
        }
    }
}
