package com.example.aaron.arrowsmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class TripSummaryActivity extends AppCompatActivity implements View.OnClickListener{

    KeyHandler selectedTrip;
    TextView tripRoute;
    TextView tripDepTime;
    TextView tripArrTime;
    TextView tripDriver;
    TextView tripPlate;
    TextView tripPassCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_summary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_trip_summary);
        setSupportActionBar(toolbar);
        setTitle("Trip Summary");
        tripRoute = (TextView) findViewById(R.id.trip_summary_route);
        tripDepTime = (TextView) findViewById(R.id.trip_summary_dep_time);
        tripArrTime = (TextView) findViewById(R.id.trip_summary_arrival_time);
        tripDriver = (TextView) findViewById(R.id.trip_summary_driver);
        tripPlate = (TextView) findViewById(R.id.trip_summary_plate_num);
        tripPassCount = (TextView) findViewById(R.id.trip_summary_passenger_count);
        Button startLanding = (Button) findViewById(R.id.start_landing_button);
        startLanding.setOnClickListener(this);
        selectedTrip = getIntent().getParcelableExtra("selectedTrip");
        populateTripSummary();
    }

    private void populateTripSummary() {
        tripRoute.setText(selectedTrip.getStringFromDB(this,
                        DBContract.Route.COLUMN_ROUTE_ORIGIN,
                        selectedTrip.getRouteID(),
                        DBContract.Route.TABLE_ROUTE,
                        DBContract.Route.COLUMN_ROUTE_ID)
                + " to " +
                selectedTrip.getStringFromDB(this,
                        DBContract.Route.COLUMN_ROUTE_DESTINATION,
                        selectedTrip.getRouteID(),
                        DBContract.Route.TABLE_ROUTE,
                        DBContract.Route.COLUMN_ROUTE_ID));

        tripDepTime.setText(selectedTrip.getStringFromDB(this,
                DBContract.Trip.COLUMN_DEP_TIME,
                selectedTrip.getTripID(),
                DBContract.Trip.TABLE_TRIP,
                DBContract.Trip.COLUMN_TRIP_ID));

        tripArrTime.setText(selectedTrip.getStringFromDB(this,
                DBContract.Trip.COLUMN_ARRIVAL_TIME,
                selectedTrip.getTripID(),
                DBContract.Trip.TABLE_TRIP,
                DBContract.Trip.COLUMN_TRIP_ID));

        tripDriver.setText(selectedTrip.getStringFromDB(this,
                        DBContract.Driver.COLUMN_FIRST_NAME,
                        selectedTrip.getDriverID(),
                        DBContract.Driver.TABLE_DRIVER,
                        DBContract.Driver.COLUMN_DRIVER_ID)
                + " " +
                selectedTrip.getStringFromDB(this,
                        DBContract.Driver.COLUMN_LAST_NAME,
                        selectedTrip.getDriverID(),
                        DBContract.Driver.TABLE_DRIVER,
                        DBContract.Driver.COLUMN_DRIVER_ID));

        tripPlate.setText(selectedTrip.getStringFromDB(this,
                DBContract.Vehicle.COLUMN_PLATE_NUM,
                selectedTrip.getVehicleID(),
                DBContract.Vehicle.TABLE_VEHICLE,
                DBContract.Vehicle.COLUMN_VEHICLE_ID));

        ArrayList<Integer> reservationList = selectedTrip.getIntIDListFromDB(this,
                DBContract.Reservation.COLUMN_RESERVATION_NUM,
                selectedTrip.getTripID(),
                DBContract.Reservation.TABLE_RESERVATION,
                DBContract.Reservation.COLUMN_TRIP_ID);
        int passengerCount = 0;
        for(int i = 0; i < reservationList.size(); i++){
            // check if the passenger has already been tapped out
            final String tapOut = selectedTrip.getStringFromDB(this,
                    DBContract.Passenger.COLUMN_TAP_OUT,
                    reservationList.get(i),
                    DBContract.Passenger.TABLE_PASSENGER,
                    DBContract.Passenger.COLUMN_RESERVATION_NUM);
            if(tapOut != null && !tapOut.equals("null")){
                passengerCount++;
            }
        }
        tripPassCount.setText("" + passengerCount);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.start_landing_button) {
            // go back to landing activity for new trip
            Intent intent = new Intent();
            intent.putExtra("next", "Landing");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
}
