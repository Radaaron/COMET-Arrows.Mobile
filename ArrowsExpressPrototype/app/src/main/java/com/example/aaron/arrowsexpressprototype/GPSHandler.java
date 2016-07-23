package com.example.aaron.arrowsexpressprototype;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class GPSHandler extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener listener;
    private double longitude, latitude;
    private String area;
    private ArrayList<GPSFence> fenceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        fenceList = new ArrayList<GPSFence>();
        populateFenceList();
        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                area = checkGPSFences(latitude, longitude);
                Intent returnArea = new Intent();
                Bundle extras = new Bundle();
                extras.putString("area", area);
                returnArea.putExtras(extras);
                setResult(RESULT_OK, returnArea);//sent result back to MainActivity
                finish();
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
        scanGPS();
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                scanGPS();
                break;
            default: break;
        }
    }

    public void scanGPS(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,1);
            }
            return;
        }
        // the following code won't execute if permissions are not allowed, because of previous return statement
        // noinspection MissingPermission
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
    }

    // fills up fenceList with GPSFence objects with parameters from Constants
    public void populateFenceList(){
        // automated in the future
        GPSFence testFence = new GPSFence(Constants.TEST_GPSFENCE_LABEL, Constants.TEST_GPSFENCE_XORIGIN,
                Constants.TEST_GPSFENCE_YORIGIN, Constants.TEST_GPSFENCE_OFFSETRADIUS);
        fenceList.add(testFence);
    }

    public String checkGPSFences(double lat, double lon){
        GPSFence temp;
        for(int i = 0; i < fenceList.size(); i++){
            temp = fenceList.get(i);
            if((lat >= temp.getyOrigin() && lat <= (temp.getyOrigin() + temp.getoffsetRadius()))
                    || (lat <= temp.getyOrigin() && lat >= (temp.getyOrigin() - temp.getoffsetRadius()))){
                if((lon >= temp.getxOrigin() && lon <= (temp.getxOrigin() + temp.getoffsetRadius()))
                        || (lon <= temp.getxOrigin() && lon >= (temp.getxOrigin() - temp.getoffsetRadius()))){
                    return temp.getAreaLabel();
                }
            }
        }
        return null;
    }

}
