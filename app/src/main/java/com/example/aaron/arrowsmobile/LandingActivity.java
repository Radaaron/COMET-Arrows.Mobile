package com.example.aaron.arrowsmobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener{

    DBHandler dbHandler;
    KeyHandler keyHandler; // used to assign plate num and driver to trip selected
    ArrayList<KeyHandler> tripList;
    TextView plateNumView, driverNameView;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.landing_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.landing_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.landing_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        plateNumView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.plateNumView);
        driverNameView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.driverNameView);
        setTitle("Upcoming Trips");

        // retrieve data for recycler view
        dbHandler = new DBHandler(this);
        tripList = dbHandler.getTripKeyHolderList();
        keyHandler = new KeyHandler();
        refreshLandingDetails();

        // commit upcoming trips fragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("tripList", tripList);
        currentFragment = new UpcomingTripsFragment();
        currentFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.landing_fragment_container, currentFragment);
        fragmentTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "" + data.getIntExtra("tripIndex", 0), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.landing_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(currentFragment instanceof UpcomingTripsFragment){
            // do nothing
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.landing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.change_plate_num) {
            ArrayList<String> plateList = keyHandler.getStringArrayListFromDB(this, DBContract.Vehicle.COLUMN_PLATE_NUM, DBContract.Vehicle.TABLE_VEHICLE);
            final String[] plateItems = plateList.toArray(new String[plateList.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(plateItems, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(DBContract.Landing.COLUMN_LANDING_PLATE_NUM, plateItems[which]);
                    db.update(DBContract.Landing.TABLE_LANDING, cv, DBContract.Landing.COLUMN_LANDING_ID + "=" + 1, null);
                    refreshLandingDetails();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Change Vehicle");
            dialog.show();
        }
        if (id == R.id.change_driver) {
            ArrayList<String> driverFirstNames = keyHandler.getStringArrayListFromDB(this, DBContract.Driver.COLUMN_FIRST_NAME, DBContract.Driver.TABLE_DRIVER);
            ArrayList<String> driverLastNames = keyHandler.getStringArrayListFromDB(this, DBContract.Driver.COLUMN_LAST_NAME, DBContract.Driver.TABLE_DRIVER);
            ArrayList<String> driverList = new ArrayList<>();
            for(int i = 0; i < driverFirstNames.size(); i++){
                driverList.add(driverFirstNames.get(i) + " " + driverLastNames.get(i));
            }
            final String[] driverItems = driverList.toArray(new String[driverList.size()]);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setItems(driverItems, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    cv.put(DBContract.Landing.COLUMN_LANDING_DRIVER, driverItems[which]);
                    db.update(DBContract.Landing.TABLE_LANDING, cv, DBContract.Landing.COLUMN_LANDING_DRIVER + "=" + 1, null);
                    refreshLandingDetails();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Change Driver");
            dialog.show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.landing_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Object object) {
        Intent intent = new Intent();
        KeyHandler selectedTrip = (KeyHandler) object;
        // update selectedTrip vehicle and driver
        selectedTrip.setVehicleID(getIdFromPlate());
        selectedTrip.setDriverID(getIdFromDriver());
        intent.putExtra("next", "Embarkation");
        intent.putExtra("selectedTrip", selectedTrip);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void refreshLandingDetails(){
        String plateNum = keyHandler.getStringFromDB(this,
                DBContract.Landing.COLUMN_LANDING_PLATE_NUM,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        String driver = keyHandler.getStringFromDB(this,
                DBContract.Landing.COLUMN_LANDING_DRIVER,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        plateNumView.setText("Current Plate: " + plateNum);
        driverNameView.setText("Current Driver: " + driver);
    }

    public String getIdFromPlate(){
        String plateNum = keyHandler.getStringFromDB(this,
                DBContract.Landing.COLUMN_LANDING_PLATE_NUM,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        return keyHandler.getStringFromDB(this, DBContract.Vehicle.COLUMN_VEHICLE_ID, plateNum, DBContract.Vehicle.TABLE_VEHICLE, DBContract.Vehicle.COLUMN_PLATE_NUM);
    }

    public int getIdFromDriver(){
        String driver = keyHandler.getStringFromDB(this,
                DBContract.Landing.COLUMN_LANDING_DRIVER,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        String[] temp = driver.split(" ");
        return keyHandler.getIntFromDB(this, DBContract.Driver.COLUMN_DRIVER_ID, temp[0], DBContract.Driver.TABLE_DRIVER, DBContract.Driver.COLUMN_FIRST_NAME);
    }

}
