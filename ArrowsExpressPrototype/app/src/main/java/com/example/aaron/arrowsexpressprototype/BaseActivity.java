package com.example.aaron.arrowsexpressprototype;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity{

    protected static ArrayList<Trip> tripList; // "protected static" means it can be used by all extended classes of BaseActivity

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Options items
        int id = item.getItemId();

        if (id == R.id.action_main) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_stored) {
            Intent intent = new Intent(this, StorageHandler.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void createTripList(){
        tripList = new ArrayList<Trip>();
    }

    public void addTrip(Trip trip){
        tripList.add(trip);
    }

    public ArrayList<Trip> getTripList(){
        return tripList;
    }

}
