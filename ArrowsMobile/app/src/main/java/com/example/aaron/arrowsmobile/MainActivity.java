package com.example.aaron.arrowsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // build database
        dbHandler = new DBHandler(this);

        // start landing process
        Intent intent = new Intent(this, LandingActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String next = data.getStringExtra("next");
            if(next.equals("Landing")){
                Intent intent = new Intent(this, LandingActivity.class);
                startActivityForResult(intent, 1);
            } else if(next.equals("Embarkation")){
                Trip selectedTrip = data.getParcelableExtra("selectedTrip");
                Intent intent = new Intent(this, EmbarkationActivity.class);
                intent.putExtra("selectedTrip", selectedTrip);
                startActivityForResult(intent, 1);
            } else if(next.equals("Trip")){
                Trip selectedTrip = data.getParcelableExtra("selectedTrip");
                Intent intent = new Intent(this, TripActivity.class);
                intent.putExtra("selectedTrip", selectedTrip);
                startActivityForResult(intent, 1);
            }
        }
    }
}
