package com.example.aaron.arrowsexpressprototype;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    public static Activity activity;
    private Button scanBtn;
    private double longitude, latitude;
    private TextView formatTxt, contentTxt;
    private TextView longitudeView, latitudeView;
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatTxt = (TextView)findViewById(R.id.scan_format);
        contentTxt = (TextView)findViewById(R.id.scan_content);
        longitudeView = (TextView) findViewById(R.id.longitudeView);
        latitudeView = (TextView) findViewById(R.id.latitudeView);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                latitude = location.getLatitude();
                return;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                // no need
            }

            @Override
            public void onProviderEnabled(String s) {
                // no need
            }

            @Override
            public void onProviderDisabled(String s) {
                // wont do anything if it is disabled. Might make it so that it redirects the user to the settings screen
            }
        };
        configure_button();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            if (!scanFormat.equals("CODE_128")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Invalid barcode type", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                formatTxt.setText("FORMAT: " + scanFormat);
                contentTxt.setText("CONTENT: " + scanContent);
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                configure_button();
                break;
            default: break;
        }
    }

    // this just configures the scan button so it wont work without the proper permissions
    void configure_button(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                        ,1);
            }
            return;
        }
        // the button click won't execute if permissions are not allowed, because of return statement
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // noinspection MissingPermission
                locationManager.requestLocationUpdates("gps", 5000, 0, listener);
                if(longitude != 0.0 && latitude != 0.0) {
                    longitudeView.append("\n " + longitude);
                    latitudeView.append("\n " + latitude);
                }
                if(view.getId()==R.id.scan_button){
                        /*ArrayList<String> format = new ArrayList<String>();
                        format.add("CODE_128");*/
                    IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
                    scanIntegrator.initiateScan();
                }
            }
        });
    }

}
