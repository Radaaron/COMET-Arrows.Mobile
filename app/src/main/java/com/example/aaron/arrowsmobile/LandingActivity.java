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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, AdapterView.OnItemSelectedListener{

    DBHandler dbHandler;
    KeyHandler keyHandler; // used to assign plate num and driver to trip selected
    ArrayList<KeyHandler> tripList;
    TextView plateNumView, driverNameView;
    Spinner routeSpinner;
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
        routeSpinner = (Spinner) findViewById(R.id.trip_route_spinner);
        routeSpinner.setOnItemSelectedListener(this);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolbarTitle = (TextView) findViewById(R.id.custom_toolbar_title);
        TextView toolbarDate = (TextView) findViewById(R.id.toolbar_date);
        toolbarTitle.setText("Upcoming Trips");
        SimpleDateFormat timeFormat = new SimpleDateFormat("MM/dd/yyyy");
        Calendar cal = Calendar.getInstance();
        toolbarDate.setText(timeFormat.format(cal.getTime()));

        // retrieve data for recycler view
        dbHandler = new DBHandler(this);
        tripList = dbHandler.getTripKeyHolderList();
        keyHandler = new KeyHandler();
        refreshLandingDetails();

        // populate spinner
        ArrayList<String> routeOrigins = keyHandler.getStringArrayListFromDB(this, DBContract.Route.COLUMN_ROUTE_ORIGIN, DBContract.Route.TABLE_ROUTE);
        ArrayList<String> routeDestinations = keyHandler.getStringArrayListFromDB(this, DBContract.Route.COLUMN_ROUTE_DESTINATION, DBContract.Route.TABLE_ROUTE);
        ArrayList<String> routeItems = new ArrayList<>();
        for(int i = 0; i < routeOrigins.size(); i++){
            routeItems.add(routeOrigins.get(i) + " to " + routeDestinations.get(i));
        }
        ArrayAdapter<String> routeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, routeItems);
        routeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        routeSpinner.setAdapter(routeAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filterTrips(getIdFromRoute(parent.getItemAtPosition(position).toString()));
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // nothing
    }

    public void filterTrips(int routeId){
        // commit upcoming trips fragment with filtered tripList
        ArrayList<KeyHandler> filteredList = new ArrayList<>();
        for(int i = 0; i < tripList.size(); i++){
            if(tripList.get(i).getRouteID() == routeId){
                filteredList.add(tripList.get(i));
            }
        }
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("filteredList", filteredList);
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
                    cv.put(DBContract.Local.COLUMN_LOCAL_PLATE_NUM, plateItems[which]);
                    db.update(DBContract.Local.TABLE_LOCAL, cv, DBContract.Local.COLUMN_LOCAL_ID + "=" + 1, null);
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
                    cv.put(DBContract.Local.COLUMN_LOCAL_DRIVER, driverItems[which]);
                    db.update(DBContract.Local.TABLE_LOCAL, cv, DBContract.Local.COLUMN_LOCAL_ID + "=" + 1, null);
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

    public void refreshLandingDetails(){
        String plateNum = keyHandler.getStringFromDB(this,
                DBContract.Local.COLUMN_LOCAL_PLATE_NUM,
                1,
                DBContract.Local.TABLE_LOCAL,
                DBContract.Local.COLUMN_LOCAL_ID);
        String driver = keyHandler.getStringFromDB(this,
                DBContract.Local.COLUMN_LOCAL_DRIVER,
                1,
                DBContract.Local.TABLE_LOCAL,
                DBContract.Local.COLUMN_LOCAL_ID);
        plateNumView.setText("Current Plate: " + plateNum);
        driverNameView.setText("Current Driver: " + driver);
    }

    public String getIdFromPlate(){
        String plateNum = keyHandler.getStringFromDB(this,
                DBContract.Local.COLUMN_LOCAL_PLATE_NUM,
                1,
                DBContract.Local.TABLE_LOCAL,
                DBContract.Local.COLUMN_LOCAL_ID);
        return keyHandler.getStringFromDB(this, DBContract.Vehicle.COLUMN_VEHICLE_ID, plateNum, DBContract.Vehicle.TABLE_VEHICLE, DBContract.Vehicle.COLUMN_PLATE_NUM);
    }

    public int getIdFromDriver(){
        String driver = keyHandler.getStringFromDB(this,
                DBContract.Local.COLUMN_LOCAL_DRIVER,
                1,
                DBContract.Local.TABLE_LOCAL,
                DBContract.Local.COLUMN_LOCAL_ID);
        String[] temp = driver.split(" ");
        return keyHandler.getIntFromDB(this, DBContract.Driver.COLUMN_DRIVER_ID, temp[0], DBContract.Driver.TABLE_DRIVER, DBContract.Driver.COLUMN_FIRST_NAME);
    }

    public int getIdFromRoute(String routeItem){
        String[] temp = routeItem.split("to ");
        return keyHandler.getIntFromDB(this, DBContract.Route.COLUMN_ROUTE_ID, temp[1], DBContract.Route.TABLE_ROUTE, DBContract.Route.COLUMN_ROUTE_DESTINATION);
    }

    @Override
    public void onFragmentInteraction(Object object) {
        Intent intent = new Intent();
        KeyHandler selectedTrip = (KeyHandler) object;
        // update selectedTrip vehicle and driver
        selectedTrip.setVehicleID(getIdFromPlate(), getApplicationContext());
        selectedTrip.setDriverID(getIdFromDriver(), getApplicationContext());
        // update trip depTime
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        Calendar cal = Calendar.getInstance();
        cv.put(DBContract.Trip.COLUMN_DEP_TIME, timeFormat.format(cal.getTime()));
        db.update(DBContract.Trip.TABLE_TRIP, cv, DBContract.Trip.COLUMN_TRIP_ID + "=" + selectedTrip.getTripID(), null);
        db.close();
        // send selectedTrip to embarkation activity
        intent.putExtra("next", "Embarkation");
        intent.putExtra("selectedTrip", selectedTrip);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
