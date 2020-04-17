package com.jvinix.iy4s.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.jvinix.iy4s.Models.Report;
import com.jvinix.iy4s.Program;
import com.jvinix.iy4s.R;
import com.jvinix.iy4s.Utils.Converter;
import com.jvinix.iy4s.ViewModels.ReportViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpAuthentication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String serverUrl;
    private GoogleMap mMap;
    private ImageView imageView;
    private File photoFile;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Report report;
    private ReportViewModel reportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        setTitle("Report Details");

        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        report = new Report();

        SharedPreferences myPreference = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        serverUrl = myPreference.getString("IPAddress", getString(R.string.default_server_address));
        int reportId = getIntent().getIntExtra("reportId", 0);

        builder = new AlertDialog.Builder(DetailsActivity.this);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        builder.setView(R.layout.myprogress_dialog);
        dialog = builder.create();
        dialog.show();

        imageView = findViewById(R.id.imageViewDetails);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //new RestTaskGetReportById().execute(new Integer[]{reportId});
        getReport(reportId);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void showReport()
    {
        TextView tvReportId = findViewById(R.id.tvReportId);
        TextView tvDescription = findViewById(R.id.tvDescription);
        TextView tvDateTime = findViewById(R.id.tvDateReport);
        TextView tvSolution = findViewById(R.id.tvSolution);

        tvReportId.setText(String.valueOf(report.getReportId()));
        tvDescription.setText(report.getDescription());
        tvDateTime.setText(Converter.LongToStringTime(report.getDateTimeReport()));
        tvSolution.setText(report.getSolution());

        if(report.getPicture() != null)
        {
            setPic(report.getPicture());
        }
        else
        {
            imageView.setVisibility(View.GONE);
        }

        Log.d("Report", report.toString());
        LatLng position = new LatLng(report.getLatitude(), report.getLongitude());
        mMap.addMarker(new MarkerOptions().position(position).title(report.getDescription()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 20));
    }

    public void getReport(final int reportId) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());  // this = context
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, serverUrl+"/api/reports/getreport/"+reportId, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("LOG_RESPONSE", response.toString());
                        Gson gson = new Gson();
                        report = gson.fromJson(response.toString(), Report.class);
                        showReport();
                        if(Program.TOKEN.equals(""))
                        {
                            reportViewModel.update(report);
                        }
                        dialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("LOG_RESPONSE", error.toString());
                        dialog.dismiss();
                        AlertDialog alertDialog = new AlertDialog.Builder(DetailsActivity.this).create();
                        alertDialog.setCancelable(false);
                        alertDialog.setTitle(getResources().getString(R.string.error));
                        alertDialog.setMessage(getResources().getString(R.string.server_error));
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                        alertDialog.show();

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


        getRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        // add it to the RequestQueue
        queue.add(getRequest);
    }

    // Submit a report to the API a new report into the database.
    public void sendUpdate()
    {
        try {
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            Gson gsonSet = new Gson();
            final String mRequestBody = gsonSet.toJson(report, Report.class);//jsonBody.toString();

            JsonObjectRequest stringRequest = new JsonObjectRequest
                    (Request.Method.PUT, serverUrl+"/api/reports/updatereport/"+report.getReportId(), null,
                            new Response.Listener<JSONObject>()
                            {
                                @Override
                                public void onResponse(JSONObject response)
                                {
                                    Log.i("LOG_RESPONSE", response.toString());
                                    dialog.dismiss();
                                    Toast.makeText(getApplication(), getString(R.string.msg_success), Toast.LENGTH_LONG).show();
                                    onBackPressed();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("LOG_RESPONSE", error.toString());
                                    dialog.dismiss();
                                    AlertDialog alertDialog = new AlertDialog.Builder(DetailsActivity.this).create();
                                    alertDialog.setCancelable(false);
                                    alertDialog.setTitle(getResources().getString(R.string.error));
                                    alertDialog.setMessage(getResources().getString(R.string.server_error));
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
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("Authorization", "Bearer "+Program.TOKEN);

                    return params;
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
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    5000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );
            requestQueue.add(stringRequest);
            Log.i("LOG_RESPONSE", stringRequest.toString());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_menu, menu);
        if (!Program.TOKEN.equals("")) return true;
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saveButtom:
                sendInvestigation();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void sendInvestigation()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Send Investigation");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogI, int which) {
                dialog.show();
                report.setSolution(report.getSolution()+".\nInvestigation Result: "+input.getText().toString());
                report.setStatusCode(2);
                report.setPicture("");
                sendUpdate();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogI, int which) {
                dialogI.cancel();
            }
        });

        builder.show();
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String imageFileName = "JPEG_currentImage";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File image = //new File(storageDir, imageFileName);
                File.createTempFile(
                        imageFileName,  /* prefix */
                        ".jpg",         /* suffix */
                        storageDir      /* directory */
                );

        // Save a file: path for use with ACTION_VIEW intents
        //newReport.setPicture(image.getAbsolutePath());
        return image;
    }

    public boolean saveBitmap(Bitmap finalBitmap) {

        photoFile = null;
        try {
            photoFile = createImageFile();
            if (photoFile.exists()) photoFile.delete ();
            FileOutputStream out = new FileOutputStream(photoFile);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, out);
            out.flush();
            out.close();
            return true;
        } catch (IOException ex) {
            // Error occurred while creating the File
            ex.printStackTrace();
            Toast.makeText(this, "ERROR creating the file.", Toast.LENGTH_LONG).show();
            return false;
        }
        //Uri tempUri = FileProvider.getUriForFile(this, getApplicationContext().getOpPackageName() + ".fileprovider", photoFile);
    }

    private void setPic(String encodeString) {

        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

       // byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);

        if (saveBitmap(Converter.StringToBitMap(encodeString)))
        {
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);

            imageView.setImageBitmap(bitmap);
        }
        else
            Toast.makeText(this, "ERROR Setting the Picture.", Toast.LENGTH_LONG).show();


    }

}
