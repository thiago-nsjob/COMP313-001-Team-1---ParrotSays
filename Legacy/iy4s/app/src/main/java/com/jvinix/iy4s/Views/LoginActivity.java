package com.jvinix.iy4s.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jvinix.iy4s.Models.Customer;
import com.jvinix.iy4s.Models.Report;
import com.jvinix.iy4s.R;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class LoginActivity extends AppCompatActivity {

    private String ServerUrl;
    private Button btnSingin;
    private Button btnRegister;
    private EditText etLogin;
    private EditText etPassword;

    private SharedPreferences myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPreference = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        ServerUrl = myPreference.getString("IPAddress", "");

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnSingin = findViewById(R.id.btnSingin);

    }

    public void Login(View view) {
        try
        {
            Customer c = new Customer();
            c.setUserName(etLogin.getText().toString());
            c.setPassword(etPassword.getText().toString());


            System.out.println(ServerUrl+"/token/");
            Customer customer = new RestTask().execute(c).get();
            System.out.println(customer);
            if(!customer.getToken().equals(""))
            {
                SharedPreferences.Editor prefEditor = myPreference.edit();
                prefEditor.putString("Token", customer.getToken());
                prefEditor.commit();
                Toast.makeText(this, "Welcome, "+etLogin.getText().toString()+"!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MyReportsActivity.class);
                intent.putExtra("statuscode", 1);
                startActivity(intent);
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                alertDialog.setCancelable(false);
                alertDialog.setTitle("ERROR");
                alertDialog.setMessage("Sign In Error. Please, try again.");

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertDialog.show();
            }
        }
        catch(Exception exc)
        {
            System.out.println("Error: "+exc.getMessage());
//            Log.e("LoginActivity", exc.getMessage());
            Toast.makeText(this, "ERROR: A server error occurred.", Toast.LENGTH_LONG).show();
        }
    }

    private class RestTask extends AsyncTask<Customer, Void, Customer> {

        @Override
        protected Customer doInBackground(Customer... customers) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                //HttpHeaders headers = new HttpHeaders();
                //headers.set("Authorization", "Bearer " + Token);

                //HttpEntity<Report> entity = new HttpEntity<Report>(newReport, headers);
                //HttpEntity<Customer> entity = new HttpEntity<Customer>(customers[0]);

                //Thread.sleep(5000);
                //return restTemplate.postForObject(ServerUrl+"/api/reports/", entity, Report.class);
                return restTemplate.postForEntity(ServerUrl+"/token/", customers[0], Customer.class).getBody();
//                RestTemplate restTemplate = new RestTemplate();
//                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//                return restTemplate.postForObject(ServerUrl + "reports/", newReport, Report.class);
            }
            catch(Exception ex)
            {
                Log.e("ERROR: ", ex.getMessage());
                return null;
            }
        }
    }
}
