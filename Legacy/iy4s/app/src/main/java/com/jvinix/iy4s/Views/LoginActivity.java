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

import com.jvinix.iy4s.Models.User;
import com.jvinix.iy4s.Models.UserDTO;
import com.jvinix.iy4s.R;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;


public class LoginActivity extends AppCompatActivity {

    private String ServerUrl;
    private Button btnSingin;
    private Button btnRegister;
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
        ServerUrl = myPreference.getString("IPAddress", "");

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

                User c = new User();
                c.setUsername(etLogin.getText().toString());
                c.setPassword(etPassword.getText().toString());
                User[] userArray = {c};

                dialog.show();
                try
                {
                    new LoginRestTask().execute(userArray);
                    //System.out.println(user);
                }
                catch(Exception exc)
                {
                    Log.e("LoginActivity", exc.getMessage());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.server_error), Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    private class LoginRestTask extends AsyncTask<User, Void, UserDTO> {

        @Override
        protected UserDTO doInBackground(User... users) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                return restTemplate.postForObject(ServerUrl+"/api/users/login", users[0], UserDTO.class);
            }
            catch(Exception ex)
            {
                Log.e("ERROR: ", ex.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(UserDTO userDTO) {
            super.onPostExecute(userDTO);

            dialog.dismiss();
            if(userDTO != null)
            {
                SharedPreferences.Editor prefEditor = myPreference.edit();
                prefEditor.putString("Token", userDTO.getToken());
                prefEditor.commit();
                Toast.makeText(getApplicationContext(),
                        getResources().getString(R.string.welcome)+", "+etLogin.getText().toString()+"!",
                        Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), ReportsActivity.class);
                intent.putExtra("statuscode", 1);
                startActivity(intent);
            }
            else
            {
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
    }
}
