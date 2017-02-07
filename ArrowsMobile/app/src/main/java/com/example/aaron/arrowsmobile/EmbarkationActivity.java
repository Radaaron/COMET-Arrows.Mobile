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
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.aaron.arrowsmobile.DBContract.Route.TABLE_ROUTE;

public class EmbarkationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, View.OnClickListener {

    DBHandler dbHandler;
    KeyHandler selectedTrip;
    Fragment currentFragment;
    String chanceDestination = null;
    String[] possibleDestinations = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embarkation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.embarkation_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.embarkation_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.embarkation_nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Button startTripButton = (Button) findViewById(R.id.start_trip_button);
        startTripButton.setOnClickListener(this);
        TextView dateView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_date_view);
        TextView timeView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_time_view);
        TextView routeView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_route_view);

        // get access to DB and retrieve trip details
        dbHandler = new DBHandler(this);
        selectedTrip = getIntent().getParcelableExtra("selectedTrip");
        possibleDestinations = getPossibleDestinations();

        dateView.setText(selectedTrip.getStringFromDB(getApplicationContext(),
                DBContract.Trip.COLUMN_TRIP_DATE,
                selectedTrip.getTripID(),
                DBContract.Trip.TABLE_TRIP,
                DBContract.Trip.COLUMN_TRIP_ID));

        timeView.setText(selectedTrip.getStringFromDB(getApplicationContext(),
                DBContract.Trip.COLUMN_DEP_TIME,
                selectedTrip.getTripID(),
                DBContract.Trip.TABLE_TRIP,
                DBContract.Trip.COLUMN_TRIP_ID));

        routeView.setText(selectedTrip.getStringFromDB(getApplicationContext(),
                DBContract.Route.COLUMN_ROUTE_NAME,
                selectedTrip.getRouteID(),
                TABLE_ROUTE,
                DBContract.Route.COLUMN_ROUTE_ID));

        // initially commit passenger manifest fragment
        setPassengerManifestFragment();
    }

    private String[] getPossibleDestinations() {
        String[] temp = new String[selectedTrip.getStopIDList().size()];
        String stopName;
        for(int i = 0; i < selectedTrip.getStopIDList().size(); i++){
            stopName = selectedTrip.getStringFromDB(getApplicationContext(),
                    DBContract.Stop.COLUMN_STOP_NAME,
                    selectedTrip.getStopIDList().get(i),
                    DBContract.Stop.TABLE_STOP,
                    DBContract.Stop.COLUMN_STOP_ID);
            temp[i] = stopName;
        }
        return temp;
    }

    public void setPassengerManifestFragment(){
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedTrip", selectedTrip);
        currentFragment = new PassengerManifestFragment();
        currentFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
        setTitle("Passenger Manifest");
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void setChanceFragment(){
        Bundle bundle = new Bundle();
        selectedTrip.refreshPassengerIDList(); // refresh list for new chance passenger
        bundle.putParcelable("selectedTrip", selectedTrip);
        currentFragment = new ChancePassengersFragment();
        currentFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
        setTitle("Chance Passengers");
        fragmentTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.embarkation_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if(currentFragment instanceof PassengerManifestFragment || currentFragment instanceof ChancePassengersFragment){
            // do nothing
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.scan_barcode, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_passenger_manifest && !(currentFragment instanceof PassengerManifestFragment)) {
            setPassengerManifestFragment();
        } else if (id == R.id.nav_chance_passengers && !(currentFragment instanceof ChancePassengersFragment)) {
            setChanceFragment();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.embarkation_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        if (resultCode == RESULT_CANCELED) {
            // no change, just refresh whatever the current fragment is
            if(currentFragment instanceof PassengerManifestFragment){
                setPassengerManifestFragment();
            } else if(currentFragment instanceof ChancePassengersFragment){
                setChanceFragment();
            }
        } else if(resultCode == RESULT_OK){
            final String id = data.getStringExtra("id");
            // check whether id is in manifest or is chance passenger
            for(int i = 0; i < selectedTrip.getPassengerIDList().size(); i++){
                if(Integer.toString(selectedTrip.getPassengerIDList().get(i)).equals(id)){
                    // set existing passenger tap in
                    Calendar cal = Calendar.getInstance();
                    ContentValues cv = new ContentValues();
                    cv.put(DBContract.Passenger.COLUMN_TAP_IN, timeFormat.format(cal.getTime()));
                    db.update(DBContract.Passenger.TABLE_PASSENGER, cv, DBContract.Passenger.COLUMN_PASSENGER_ID + "=" +id, null);
                    // refresh passenger manifest
                    setPassengerManifestFragment();
                } else{
                    // get chance passenger destination
                    final String[] destinations = possibleDestinations;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Choose Chance Passenger Destination");
                    builder.setItems(destinations, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            chanceDestination = destinations[which];
                            // add new passenger
                            Calendar cal = Calendar.getInstance();
                            SQLiteDatabase db = dbHandler.getWritableDatabase();
                            ContentValues cv = new ContentValues();
                            cv.put(DBContract.Passenger.COLUMN_PASSENGER_ID, id);
                            cv.put(DBContract.Passenger.COLUMN_FEEDBACK_ON, "");
                            cv.put(DBContract.Passenger.COLUMN_FEEDBACK, 0);
                            cv.put(DBContract.Passenger.COLUMN_TAP_IN, timeFormat.format(cal.getTime()));
                            cv.put(DBContract.Passenger.COLUMN_TAP_OUT, (byte[]) null);
                            // get any manifest passenger's disembarkation point since they all have the same disembarkation point
                            cv.put(DBContract.Passenger.COLUMN_DISEMBARKATION_PT, selectedTrip.getStringFromDB(getApplicationContext(),
                                    DBContract.Passenger.COLUMN_DISEMBARKATION_PT,
                                    selectedTrip.getPassengerIDList().get(0),
                                    DBContract.Passenger.TABLE_PASSENGER,
                                    DBContract.Passenger.COLUMN_PASSENGER_ID));
                            cv.put(DBContract.Passenger.COLUMN_DESTINATION, chanceDestination);
                            cv.put(DBContract.Passenger.COLUMN_IS_CHANCE, true);
                            db.insert(DBContract.Passenger.TABLE_PASSENGER, null, cv);
                            // refresh chance passengers
                            setChanceFragment();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            chanceDestination = null;
                            // refresh chance passengers
                            setChanceFragment();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }

    // checks the remaining space by vehicle capacity minus the number of tapped in passengers
    public int remainingSpace(KeyHandler selectedTrip){
        int capacity = selectedTrip.getIntFromDB(getApplicationContext(),
                DBContract.Vehicle.COLUMN_CAPACITY,
                selectedTrip.getVehicleID(),
                DBContract.Vehicle.TABLE_VEHICLE,
                DBContract.Vehicle.COLUMN_VEHICLE_ID);
        for(int i = 0; i < selectedTrip.getPassengerIDList().size(); i++){
            if(selectedTrip.getStringFromDB(getApplicationContext(),
                    DBContract.Passenger.COLUMN_TAP_IN,
                    selectedTrip.getPassengerIDList().get(i),
                    DBContract.Passenger.TABLE_PASSENGER,
                    DBContract.Passenger.COLUMN_PASSENGER_ID) != null){
                capacity--;
            }
        }
        return capacity;
    }

    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.start_trip_button) {
            int free = remainingSpace(selectedTrip);
            if(free == 0) {
                Intent intent = new Intent();
                intent.putExtra("next", "Trip");
                intent.putExtra("selectedTrip", selectedTrip);
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("You still have " + free + " slots to fill!");
                builder.setPositiveButton("Start Trip anyway", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.putExtra("next", "Trip");
                        intent.putExtra("selectedTrip", selectedTrip);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Pick up Chance Passengers", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable("selectedTrip", selectedTrip);
                        setChanceFragment();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    @Override
    public void onFragmentInteraction(Object object) {
        // empty
    }
}
