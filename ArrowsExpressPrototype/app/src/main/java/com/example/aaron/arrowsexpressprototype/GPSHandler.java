package com.example.aaron.arrowsexpressprototype;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

public class GPSHandler  extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener listener;
    private double longitude, latitude;

    public GPSHandler(Object systemService){
        locationManager = (LocationManager) systemService;

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
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                getGPS();
                break;
            default: break;
        }
    }

    public void getGPS(){
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.INTERNET}
                        ,1);
            }
            return;
        }
        // the following code won't execute if permissions are not allowed, because of previous return statement
        // noinspection MissingPermission
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);
        finish();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
