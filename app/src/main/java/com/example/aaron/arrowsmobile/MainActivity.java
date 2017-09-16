package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements OnNetworkSuccessListener{

    DBHandler dbHandler;
    KeyHandler keyHandler;
    NetworkHandler networkHandler;
    TextView statusText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inbetween_layout);
        statusText = (TextView) findViewById(R.id.statusLabel);
        // build database
        dbHandler = new DBHandler(this);
        dbHandler.createAppTables();
        keyHandler = new KeyHandler();
        // date checking
        checkDate();
        // check if landing details are not present
        checkLandingDetails();
    }

    public void checkDate(){
        try {
            String date = keyHandler.getStringFromDB(this, DBContract.Local.COLUMN_LOCAL_DATE, 1, DBContract.Local.TABLE_LOCAL, DBContract.Local.COLUMN_LOCAL_ID);
            if (date != null) {
                Log.e(TAG, "DATE FOUND");
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar cal = Calendar.getInstance();
                Date dateToday, dateDb;
                dateToday = dateFormat.parse(dateFormat.format(cal.getTime()));
                dateDb = dateFormat.parse(date);
                // if its a new day, delete old db
                if (dateToday.after(dateDb)) {
                    Log.e(TAG, "PREV DATE FOUND");
                    getApplicationContext().deleteDatabase("arrowsDB.db");
                    dbHandler = new DBHandler(this);
                    dbHandler.createAppTables();
                    // input new date
                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(DBContract.Local.COLUMN_LOCAL_DATE, dateFormat.format(cal.getTime()));
                    db.update(DBContract.Local.TABLE_LOCAL, cv, DBContract.Local.COLUMN_LOCAL_ID + "=" + 1, null);
                    db.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void checkLandingDetails(){
        try {
            if(keyHandler.getStringFromDB(this, DBContract.Local.COLUMN_LOCAL_PLATE_NUM, 1, DBContract.Local.TABLE_LOCAL, DBContract.Local.COLUMN_LOCAL_ID) == null || keyHandler.getStringFromDB(this, DBContract.Local.COLUMN_LOCAL_PLATE_NUM, 1, DBContract.Local.TABLE_LOCAL, DBContract.Local.COLUMN_LOCAL_ID) == null){
                Log.e(TAG, "LANDING DETAILS NOT FOUND");
                networkHandler = new NetworkHandler(this);
                statusText.setText("Downloading JSON Data...");
                networkHandler.volleyGetRequest(this);
            }
            else{
                Log.e(TAG, "LANDING DETAILS FOUND");
                startNextActivity("Landing", null);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onNetworkSuccess(Boolean b) {
        if(b){
            startUpdates();
            Intent intent = new Intent(this, LandingInputActivity.class);
            startActivityForResult(intent, 1);
        } else {
            statusText.setText("Network Error!!!");
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
        } else if(next.equals("End")){
            // post to server
            statusText.setText("Sending Data...");
            networkHandler.volleyPostRequest(getApplicationContext());
            // stop updates
            stopUpdates();
            statusText.setText("No More Trips!!!");
        }
    }

    public void startUpdates(){
        Intent intent = new Intent(this, UpdateService.class);
        startService(intent);
    }

    public void stopUpdates(){
        Intent intent = new Intent(this, UpdateService.class);
        stopService(intent);
    }

}
