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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jvinix.iy4s.Models.Report;
import com.jvinix.iy4s.Program;
import com.jvinix.iy4s.R;
import com.jvinix.iy4s.Utils.Converter;
import com.jvinix.iy4s.ViewModels.ReportViewModel;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsActivity extends AppCompatActivity {

    private String serverUrl;
    private ReportViewModel rvm;
    private ListView listView;
    private List<Report> reportsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reports);

        SharedPreferences myPreference = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        serverUrl = myPreference.getString("IPAddress", getString(R.string.default_server_address));

        rvm = ViewModelProviders.of(this).get(ReportViewModel.class);

        reportsList = new ArrayList<Report>();
        listView = findViewById(R.id.lvReports);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent i = new Intent(getApplicationContext(), DetailsActivity.class);
                i.putExtra("reportId", reportsList.get(position).getReportId());
                startActivity(i);

            }});
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        // If statusCode is 1, the user has logged in as Security Guard,
        // otherwise, he/she if a regular user
        if (!Program.TOKEN.equals(""))
        {
            // Shows a list of reports to be investigated.
            setTitle(getResources().getString(R.string.reports_to_investigate));
            getReport();
        }
        else {
            // Shows all reports submitted by the user.
            setTitle(getResources().getString(R.string.reports));
            reportsList = rvm.getAllReports();
            listView.setAdapter(createAdapter(reportsList));
        }
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

                String status = "";
                switch (finalList.get(position).getStatusCode())
                {
                    case 0 : status = getString(R.string.opened);
                        break ;
                    case 1 : status = getString(R.string.investigation_requested);
                        break ;
                    case 2 : status = getString(R.string.investigation_returned);
                        break ;
                    case 3 : status = getString(R.string.sol_requested);
                        break ;
                    case 4 : status = getString(R.string.solved);
                        break ;
                }

                text1.setText("Report Id: "+finalList.get(position).getReportId());
                text2.setText("Current Status: "+status+"\nReport Date: "+ Converter.LongToStringTime(finalList.get(position).getDateTimeReport())+"\nDescription: "+finalList.get(position).getDescription());
                return view;
            }
        };
        return adapter;
    }

    public void getReport() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());  // this = context
        // prepare the Request
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, serverUrl+"/api/reports/getbystatus/1", null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("ReportActivity", response.toString());
                        Gson gson = new Gson();
                        Report[] reports = gson.fromJson(response.toString(), Report[].class);
                        //showReport(report);
                        reportsList = Arrays.asList(reports);
                        listView.setAdapter(createAdapter(reportsList));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ReportActivity Error", error.toString());
                    }
                }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Bearer "+Program.TOKEN);

                return params;
            }
        };
        // add it to the RequestQueue
        queue.add(getRequest);
    }

}
