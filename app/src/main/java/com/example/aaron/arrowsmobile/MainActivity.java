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
        setContentView(R.layout.loading_layout);
        // build database
        dbHandler = new DBHandler(this);
        dbHandler.createAppTables();
        keyHandler = new KeyHandler();
        // check if landing details are present
        if(keyHandler.getStringFromDB(this, DBContract.Local.COLUMN_LOCAL_PLATE_NUM, 1, DBContract.Local.TABLE_LOCAL, DBContract.Local.COLUMN_LOCAL_ID) == null || keyHandler.getStringFromDB(this, DBContract.Local.COLUMN_LOCAL_PLATE_NUM, 1, DBContract.Local.TABLE_LOCAL, DBContract.Local.COLUMN_LOCAL_ID) == null){
            try {
                NetworkHandler networkHandler = new NetworkHandler(this);
                networkHandler.volleyGetRequest(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            startNextActivity("Landing", null);
        }
    }

    @Override
    public void onNetworkSuccess(Boolean b) {
        if(b){
            Intent intent = new Intent(this, LandingInputActivity.class);
            startActivityForResult(intent, 1);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String next = data.getStringExtra("next");
            if(next.equals("Landing")){
                startNextActivity(next, null);
            } else {
                KeyHandler selectedTrip = data.getParcelableExtra("selectedTrip");
                startNextActivity(next, selectedTrip);
            }
        }
    }

    public void startNextActivity(String next, KeyHandler selectedTrip){
        if(next.equals("Landing")){
            Intent intent = new Intent(this, LandingActivity.class);
            startActivityForResult(intent, 1);
        } else if(next.equals("Embarkation")){
            Intent intent = new Intent(this, EmbarkationActivity.class);
            intent.putExtra("selectedTrip", selectedTrip);
            startActivityForResult(intent, 1);
        } else if(next.equals("Trip")){
            Intent intent = new Intent(this, TripActivity.class);
            intent.putExtra("selectedTrip", selectedTrip);
            startActivityForResult(intent, 1);
        } else if(next.equals("Summary")){
            Intent intent = new Intent(this, TripSummaryActivity.class);
            intent.putExtra("selectedTrip", selectedTrip);
            startActivityForResult(intent, 1);
        }
    }

}
