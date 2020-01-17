package com.jvinix.iy4s.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.jvinix.iy4s.Models.Report;
import com.jvinix.iy4s.R;
import com.jvinix.iy4s.Utils.Converter;
import com.jvinix.iy4s.ViewModels.ReportViewModel;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String ServerUrl;// = "http://192.168.2.32:8167";
    private String Token = "70dbec0f-36fb-476b-971b-2167fec858de";
    private GoogleMap mGoogleMap;
    private SupportMapFragment mapFrag;
    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private FusedLocationProviderClient mFusedLocationClient;

    private ReportViewModel reportViewModel;
    private Report newReport;



    private static final int REQUEST_TAKE_PHOTO = 1;

    private ImageView imageView;
    private File photoFile;
    private EditText editMult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setTitle("Parrot Says"); // for set actionbar title

        SharedPreferences myPreference = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        ServerUrl = myPreference.getString("IPAddress", "");



        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        newReport = new Report();

        editMult = findViewById(R.id.etMulti);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        imageView = findViewById(R.id.imageView);

        mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.about:

                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            case R.id.myReports:

                intent = new Intent(this, MyReportsActivity.class);
                startActivity(intent);
                return true;

            case R.id.SignIn:

                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // Method called by the button "Submit"
    public void submitReport(View view)
    {

        // It's not allowed to add report without a description.
        if(!editMult.getText().toString().equals("")) {

            if(saveReport()) {
                AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                alertDialog.setCancelable(false);
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("What you said was submited successfully!");

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                imageView.setImageResource(0);
                                editMult.setText("");
                            }
                        });
                alertDialog.show();
                imageView.setImageResource(0);
                editMult.setText("");
                newReport = new Report();
                Toast.makeText(this, "What you said was submited successfully!", Toast.LENGTH_LONG).show();
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(MapsActivity.this).create();
                alertDialog.setCancelable(false);
                alertDialog.setTitle("ERROR");
                alertDialog.setMessage("A server error happened. Please check the log and try again.");

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertDialog.show();
            }

        }
        else
        {
            Toast.makeText(this, "Please, type a description...", Toast.LENGTH_LONG).show();
        }
    }

    // Adds a new report into the database and return true.
    // If some error happens it register the error in log and return false.
    public boolean saveReport()
    {
        try
        {
            newReport.setDescription(editMult.getText().toString());
            newReport.setDateTimeReport(new Date().getTime());
            Report result = new RestTask().execute().get();

            if(result != null) {
                reportViewModel.insert(result);
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception ex)
        {
            Log.e("ERROR: ", ex.getMessage());
        }
        return false;
    }

    private class RestTask extends AsyncTask<Void, Void, Report>{

        @Override
        protected Report doInBackground(Void... voids) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

                HttpHeaders headers = new HttpHeaders();
                headers.set("Authorization", "Bearer " + Token);

                HttpEntity<Report> entity = new HttpEntity<Report>(newReport, headers);

                //Thread.sleep(5000);
                return restTemplate.postForObject(ServerUrl+"/api/reports/", entity, Report.class);

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


    // Changes the type of the map.
    private boolean typeSat = false;
    public void changeMapType(View view)
    {
        if (!typeSat) {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            typeSat = true;
        }
        else {
            mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            typeSat = false;
        }
    }

    // Changes the size of the map.
    private boolean zoom = false;
    public void changeMapSize(View view)
    {
        if(zoom)
        {
            zoom = false;
        }
        else
        {
            zoom = true;
        }
        resizeMap();
    }

    // Resizes the map UI element to full screen or half of the screen.
    private void resizeMap()
    {
        int weight;
        if (zoom) {
            weight = 1;
        }
        else
        {
            if (imageView.getVisibility() == View.VISIBLE) {
                weight = 70;
            }
            else
            {
                weight = 50;
            }
        }

        RelativeLayout rlMap = findViewById(R.id.rlMap);
        LinearLayout.LayoutParams param;
            param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    weight);
        rlMap.setLayoutParams(param);
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
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
        mGoogleMap = googleMap;
        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(120000); // two minute interval
        mLocationRequest.setFastestInterval(120000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                Log.i("MapsActivity", "Location: " + location.getLatitude() + " " + location.getLongitude());
                mLastLocation = location;
                if (mCurrLocationMarker != null) {
                    mCurrLocationMarker.remove();
                }

                //Gets latitude and longitude values based on GPS Location.
                LatLng latLng = getLatLng(location);

                // Moves the map to specific Latitude and Longitude values.
                moveMapTo(latLng);
            }
        }
    };

    // Sets the Latitude and Longitude in the report object
    // to be added when the user click on Submit button.
    // Then return a LatLng object
    private LatLng getLatLng(Location location)
    {
        newReport.setLatitude(location.getLatitude());
        newReport.setLongitude(location.getLongitude());

        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    // Based on LatLnd object creates point marker on the map,
    // and move the map view to that point.
    private void moveMapTo(LatLng position)
    {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 20));
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            if (photoFile.exists()) {
                //Toast.makeText(this, "File was sabed at "+ photoFile.getAbsolutePath(), Toast.LENGTH_LONG).show();
                galleryAddPic();
                imageView.setVisibility(View.VISIBLE);
                setPic();
                resizeMap();
            }
            else
            {
                Toast.makeText(this, "ERROR saving the file.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
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


    public void process(View view)
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File
            Toast.makeText(this, "ERROR creating the file.", Toast.LENGTH_LONG).show();
        }
        Uri tempUri = FileProvider.getUriForFile(this, getApplicationContext().getOpPackageName() + ".fileprovider", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = photoFile.getAbsoluteFile(); //new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
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

        //Bitmap bitmap = BitmapFactory.decodeFile(newReport.getPicturePath(), bmOptions);
        Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);

        newReport.setPicture(Converter.BitmapToByte(BitmapFactory.decodeFile(photoFile.getAbsolutePath())));

        imageView.setImageBitmap(bitmap);
    }
}
