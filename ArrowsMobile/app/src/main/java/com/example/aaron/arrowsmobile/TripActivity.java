package com.example.aaron.arrowsmobile;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class TripActivity extends AppCompatActivity implements View.OnClickListener, OnFragmentInteractionListener, OnTripStopListener{

    DBHandler dbHandler;
    Trip selectedTrip;
    ArrayList<Passenger> passengerList;
    Fragment currentFragment;
    LocationManager locationManager;
    GPSHandler gpsHandler;
    Button nextStopButton;
    String currentStop = null;
    String nextStop = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.trip_toolbar);
        setSupportActionBar(toolbar);
        nextStopButton = (Button) findViewById(R.id.next_stop_button);
        nextStopButton.setOnClickListener(this);
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);

        // get access to DB and retrieve trip details
        dbHandler = new DBHandler(this);
        selectedTrip = getIntent().getParcelableExtra("selectedTrip");
        gpsHandler = new GPSHandler(selectedTrip.getTripSched().getRoute().getRouteStopList(), this);
        passengerList = selectedTrip.getPassengerList();
        setTitle("Trip Route: " + selectedTrip.getTripSched().getRoute().getRouteName());
        // set first stop text
        nextStop = selectedTrip.getTripSched().getRoute().getRouteStop(0).getStop().getStopName();
        nextStopButton.setText("Next Stop: " + nextStop);
        nextStopButton.setEnabled(false);
        // start scanning
        scanGPS();

        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedTrip", selectedTrip);
        currentFragment = new OnTripPassengersFragment();
        currentFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.trip_fragment_container, currentFragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan_barcode, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if(currentFragment instanceof OnTripPassengersFragment){
            // do nothing
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_scan_barcode) {
            Intent intent = new Intent(this, ScanActivity.class);
            startActivityForResult(intent, 1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            // nothing lol
        } else if(resultCode == RESULT_OK){
            String id = data.getStringExtra("id");
            for(int i = 0; i < selectedTrip.getPassengerCount(); i++){
                if(Integer.toString(selectedTrip.getPassenger(i).getPassengerID()).equals(id)){
                    // set passenger tap out
                    Calendar cal = Calendar.getInstance();
                    selectedTrip.getPassenger(i).setTapOut(cal);
                }
            }
            // resume scanning
            scanGPS();
            // refresh page
            Bundle bundle = new Bundle();
            bundle.putParcelable("selectedTrip", selectedTrip);
            currentFragment = new OnTripPassengersFragment();
            currentFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.trip_fragment_container, currentFragment);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        scanGPS();
    }


    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.next_stop_button) {
            Calendar cal = Calendar.getInstance();
            selectedTrip.setArrivalTime(cal);
            Intent intent = new Intent();
            intent.putExtra("next", "Landing");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
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
        locationManager.requestLocationUpdates("gps", 5000, 0, gpsHandler);
    }

    @Override
    public void onFragmentInteraction(Object object) {
        // nothing
    }

    @Override
    public void OnTripStopArrival(String stopName) {
        if(stopName != null){
            if(stopName.equals("off")){
                Toast.makeText(this, "GPS Disabled", Toast.LENGTH_LONG).show();
                nextStopButton.setEnabled(false);
            }
            else{
                // set currentStop and nextStop if there is, if the currentStop is the last stop, set nextStop to null and enable end trip button
                int i = 0;
                int nextStopPosition = 0;
                boolean found = false;
                while(i < selectedTrip.getTripSched().getRoute().getRouteStopList().size() && !(found)){
                    if(selectedTrip.getTripSched().getRoute().getRouteStop(i).getStop().getStopName().equals(stopName)){
                        currentStop = selectedTrip.getTripSched().getRoute().getRouteStop(i).getStop().getStopName();
                        found = true;
                        nextStopPosition = i + 1;
                    }
                    i++;
                }
                if(nextStopPosition == selectedTrip.getTripSched().getRoute().getRouteStopList().size()){
                    nextStop = null;
                    nextStopButton.setText("End Trip");
                    nextStopButton.setEnabled(true);
                } else {
                    nextStop = selectedTrip.getTripSched().getRoute().getRouteStop(nextStopPosition).getStop().getStopName();
                    nextStopButton.setText("Next Stop: " + nextStop);
                }
            }
        }else{
            // disable button if not near trip end
            nextStopButton.setEnabled(false);
        }
    }
}
