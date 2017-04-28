package com.example.aaron.arrowsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity implements OnNetworkSuccessListener{

    DBHandler dbHandler;
    KeyHandler keyHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // build database
        dbHandler = new DBHandler(this);
        try {
            NetworkHandler networkHandler = new NetworkHandler(this, this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String next = data.getStringExtra("next");
            if(next.equals("Landing")){
                Intent intent = new Intent(this, LandingActivity.class);
                startActivityForResult(intent, 1);
            } else if(next.equals("Embarkation")){
                KeyHandler selectedTrip = data.getParcelableExtra("selectedTrip");
                Intent intent = new Intent(this, EmbarkationActivity.class);
                intent.putExtra("selectedTrip", selectedTrip);
                startActivityForResult(intent, 1);
            } else if(next.equals("Trip")){
                KeyHandler onTrip = data.getParcelableExtra("selectedTrip");
                Intent intent = new Intent(this, TripActivity.class);
                intent.putExtra("selectedTrip", onTrip);
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    public void onNetworkSuccess(Boolean b) {
        if(b){
            keyHandler = new KeyHandler();
            // check if new day
            if(keyHandler.getStringFromDB(this, DBContract.Landing.COLUMN_LANDING_PLATE_NUM, 1, DBContract.Landing.TABLE_LANDING, DBContract.Landing.COLUMN_LANDING_ID) == null || keyHandler.getStringFromDB(this, DBContract.Landing.COLUMN_LANDING_PLATE_NUM, 1, DBContract.Landing.TABLE_LANDING, DBContract.Landing.COLUMN_LANDING_ID) == null){
                Intent intent = new Intent(this, LandingInputActivity.class);
                startActivityForResult(intent, 1);
            }
            else{
                // continue landing process
                Intent intent = new Intent(this, LandingActivity.class);
                startActivityForResult(intent, 1);
            }
        }
    }
}
