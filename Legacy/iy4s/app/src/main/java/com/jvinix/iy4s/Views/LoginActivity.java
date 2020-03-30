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

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.jvinix.iy4s.Models.User;
import com.jvinix.iy4s.Models.UserDTO;
import com.jvinix.iy4s.Program;
import com.jvinix.iy4s.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    private String ServerUrl;
    private Button btnSingin;
    private EditText etLogin;
    private EditText etPassword;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;

    private SharedPreferences myPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        myPreference = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        ServerUrl = myPreference.getString("IPAddress", getString(R.string.default_server_address));

        builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.myprogress_dialog);
        dialog = builder.create();

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        btnSingin = findViewById(R.id.btnSingin);
        btnSingin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
                try
                {
                    //Proceed to Login
                    doLogin(etLogin.getText().toString(), etPassword.getText().toString());
                }
                catch(Exception exc)
                {
                    Log.e("LoginActivity", exc.getMessage());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Login method deal with API.
    public void doLogin(String username, String password) {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("username", username);
            jsonBody.put("password", password);
            final String mRequestBody = jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest
                    (Request.Method.POST, ServerUrl+"/api/users/login", null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response)
                            {
                                Log.i("LOG_RESPONSE", response.toString());
                                dialog.dismiss();

                                try {
                                    Program.TOKEN = response.getString("token");

                                    Toast.makeText(getApplicationContext(),
                                            getResources().getString(R.string.welcome)+", "+etLogin.getText().toString()+"!",
                                            Toast.LENGTH_LONG).show();

                                    Intent intent = new Intent(getApplicationContext(), ReportsActivity.class);
                                    startActivity(intent);
                                } catch (JSONException e) {
                                    Log.e("LOG_RESPONSE", e.getMessage());
                                    Toast.makeText(getApplicationContext(),
                                            getString(R.string.alert)+" - "+getString(R.string.login_error),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.e("LOG_RESPONSE", error.toString());
                                dialog.dismiss();
                                AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
                                alertDialog.setCancelable(false);
                                alertDialog.setTitle(getResources().getString(R.string.alert));
                                alertDialog.setMessage(getResources().getString(R.string.login_error));

                                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {

                                            }
                                        });
                                alertDialog.show();
                            }
                        }
                    )
            {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    return super.parseNetworkResponse(response);
                }

                //                @Override
//                protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                    String responseString = "";
//                    if (response != null) {
//                        responseString = String.valueOf(response.statusCode);
//                    }
//                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//                }
            };

            requestQueue.add(stringRequest);
            Log.i("LOG_RESPONSE", stringRequest.toString());
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
