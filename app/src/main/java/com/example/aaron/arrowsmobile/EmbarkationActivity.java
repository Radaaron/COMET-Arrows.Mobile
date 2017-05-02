package com.example.aaron.arrowsmobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EmbarkationActivity extends AppCompatActivity
        implements OnFragmentInteractionListener, View.OnClickListener {

    DBHandler dbHandler;
    KeyHandler selectedTrip;
    Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embarkation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.embarkation_toolbar);
        setSupportActionBar(toolbar);
        Button startTripButton = (Button) findViewById(R.id.start_trip_button);
        startTripButton.setOnClickListener(this);

        // get access to DB and retrieve trip details
        dbHandler = new DBHandler(this);
        selectedTrip = getIntent().getParcelableExtra("selectedTrip");

        // initially commit passenger manifest fragment
        setPassengerManifestFragment();
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

    @Override
    public void onBackPressed() {
        if(currentFragment instanceof PassengerManifestFragment){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        if (resultCode == RESULT_CANCELED) {
            // no change, just refresh whatever the current fragment is
            if(currentFragment instanceof PassengerManifestFragment){
                setPassengerManifestFragment();
            }
        } else if(resultCode == RESULT_OK){
            String id = data.getStringExtra("id");
            // check whether id is in manifest or is chance passenger
            for(int i = 0; i < selectedTrip.getPassengerIDList().size(); i++){
                if(Integer.toString(selectedTrip.getPassengerIDList().get(i)).equals(id)){
                    // set existing passenger tap in time, driver and vehicle
                    Calendar cal = Calendar.getInstance();
                    ContentValues cv = new ContentValues();
                    cv.put(DBContract.Passenger.COLUMN_TAP_IN, timeFormat.format(cal.getTime()));
                    cv.put(DBContract.Passenger.COLUMN_PASSENGER_DRIVER, selectedTrip.getDriverID());
                    cv.put(DBContract.Passenger.COLUMN_PASSENGER_VEHICLE, selectedTrip.getVehicleID());
                    db.update(DBContract.Passenger.TABLE_PASSENGER, cv, DBContract.Passenger.COLUMN_PASSENGER_ID + "=" +id, null);
                    // refresh passenger manifest
                    setPassengerManifestFragment();
                } else{
                    Toast.makeText(this, "ID is not part of this trip!", Toast.LENGTH_LONG).show();
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
                builder.setMessage("Are you sure?");
                builder.setPositiveButton("Start Trip", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.putExtra("next", "Trip");
                        intent.putExtra("selectedTrip", selectedTrip);
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
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
