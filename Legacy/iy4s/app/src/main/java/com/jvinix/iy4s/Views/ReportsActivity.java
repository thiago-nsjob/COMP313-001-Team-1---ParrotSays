package com.jvinix.iy4s.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

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

import com.jvinix.iy4s.Models.Report;
import com.jvinix.iy4s.R;
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

public class ReportsActivity extends AppCompatActivity {

    private String ServerUrl;
    private String Token;
    private ReportViewModel rvm;
    private ListView listView;

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

        listView = findViewById(R.id.lvReports);

        try {
            // If statusCode is 1, the user has logged in as Security Guard,
            // otherwise, he/she if a regular user
            if (statuscode == 1)
            {
                // Shows a list of reports to be investigated.
                setTitle(getResources().getString(R.string.reports_to_investigate));
                new RestTaskGetAllByStatusCode().execute();
            }
            else {
                // Shows all reports submitted by the user.
                listView.setAdapter(createAdapter(rvm.getAllReports()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // To be implemented.

            }});
    }


    private ArrayAdapter createAdapter(List<Report> list)
    {
        final List<Report> finalList = list;
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
        return adapter;
    }

    class RestTaskGetAllByStatusCode extends AsyncTask<Integer, Void, List<Report>> {

        @Override
        protected List<Report> doInBackground(Integer... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer "+Token);
                System.out.println(Token);
                HttpEntity<String> entity = new HttpEntity<String>("tokens",headers);

                ResponseEntity<Report[]> re = restTemplate.exchange(ServerUrl+"/api/reports/getbystatus/1", HttpMethod.GET, entity, Report[].class);
                return Arrays.asList(re.getBody());

            }
            catch(Exception ex)
            {
                Log.e("ERROR: ", ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Report> reports) {
            super.onPostExecute(reports);

            if (reports != null)
                listView.setAdapter(createAdapter(reports));
        }
    }
}
