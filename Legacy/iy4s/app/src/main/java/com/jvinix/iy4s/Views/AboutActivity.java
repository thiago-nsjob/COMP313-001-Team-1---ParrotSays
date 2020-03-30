package com.jvinix.iy4s.Views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jvinix.iy4s.R;

public class AboutActivity extends AppCompatActivity {

    private EditText etIPAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("About this App");
        etIPAddress = findViewById(R.id.etIPAddress);
        SharedPreferences myPreference = getSharedPreferences("MyPrefs",MODE_PRIVATE);

        etIPAddress.setText(myPreference.getString("IPAddress", getString(R.string.default_server_address)));

        TextView txtTeam = findViewById(R.id.txtTeam);
        for(String str : getResources().getStringArray(R.array.teamMembers))
        {
            txtTeam.setText(txtTeam.getText() + str +"\n");
        }

    }

    public void saveIp(View view)
    {
        SharedPreferences myPreference = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = myPreference.edit();
        if(!etIPAddress.getText().toString().equals("")){
            prefEditor.putString("IPAddress", etIPAddress.getText().toString());
        }else{
            prefEditor.putString("IPAddress", getString(R.string.default_server_address));
            etIPAddress.setText(getString(R.string.default_server_address));
        }

        prefEditor.commit();
        Toast.makeText(this, "IP Address saved.", Toast.LENGTH_LONG).show();

        finish();
    }
}
