package com.example.aaron.arrowsmobile;


import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class GPSHandler implements LocationListener {

    private double longitude, latitude;
    private String stopName;
    private ArrayList<GPSFence> fenceList = null;
    private OnTripStopListener mListener;

    GPSHandler(ArrayList<Stop> stopList, OnTripStopListener listener){
        fenceList = new ArrayList<>();
        populateFenceList(stopList);
        mListener = listener;
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        stopName = checkGPSFences(latitude, longitude);
        Log.e(TAG, "Lat: " + latitude + " Long: " + longitude + " stopName: " + stopName);
        mListener.OnTripStopArrival(stopName);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        mListener.OnTripStopArrival("off");
    }

    public String checkGPSFences(double latitude, double longitude){
        GPSFence temp;
        for(int i = 0; i < fenceList.size(); i++){
            temp = fenceList.get(i);
            if((latitude >= temp.getyOrigin() && latitude <= (temp.getyOrigin() + temp.getoffsetRadius()))
                    || (latitude <= temp.getyOrigin() && latitude >= (temp.getyOrigin() - temp.getoffsetRadius()))){
                if((longitude >= temp.getxOrigin() && longitude <= (temp.getxOrigin() + temp.getoffsetRadius()))
                        || (longitude <= temp.getxOrigin() && longitude >= (temp.getxOrigin() - temp.getoffsetRadius()))){
                    return temp.getStopName();
                }
            }
        }
        return null;
    }

    // fills up fenceList with GPSFence objects with parameters from Constants
    public void populateFenceList(ArrayList<Stop> stopList){
        for(int i = 0; i < stopList.size(); i++){
            // 0.01 = 1.1132 km radius
            GPSFence fence = new GPSFence(stopList.get(i).getStopName(), Double.parseDouble(stopList.get(i).getLongitude()), Double.parseDouble(stopList.get(i).getLatitude()), 0.01);
            fenceList.add(fence);
        }
    }

}
