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
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener{

    DBHandler dbHandler;
    KeyHandler tripHelper; // used to assign plate num and driver to trip selected
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
        tripHelper = new KeyHandler();

        checkPlateNumDriver();
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
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(!input.getText().toString().equals("")) {
                        SQLiteDatabase db = dbHandler.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(DBContract.Landing.COLUMN_LANDING_PLATE_NUM, input.getText().toString());
                        db.update(DBContract.Landing.TABLE_LANDING, cv, DBContract.Landing.COLUMN_LANDING_ID + "=" + 1, null);
                        refreshLandingDetails();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Enter Vehicle Plate Number");
            dialog.show();
        }
        if (id == R.id.change_driver) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);
            builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(!input.getText().toString().equals("")) {
                        SQLiteDatabase db = dbHandler.getWritableDatabase();
                        ContentValues cv = new ContentValues();
                        cv.put(DBContract.Landing.COLUMN_LANDING_DRIVER, input.getText().toString());
                        db.update(DBContract.Landing.TABLE_LANDING, cv, DBContract.Landing.COLUMN_LANDING_ID + "=" + 1, null);
                        refreshLandingDetails();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.setTitle("Enter Driver Nickname");
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
        intent.putExtra("next", "Embarkation");
        intent.putExtra("selectedTrip", selectedTrip);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    // checks if there is a plate num and driver already chosen for the trip
    public void checkPlateNumDriver(){
        if(tripHelper.getStringFromDB(this, DBContract.Landing.COLUMN_LANDING_PLATE_NUM, 1, DBContract.Landing.TABLE_LANDING, DBContract.Landing.COLUMN_LANDING_ID) == null || tripHelper.getStringFromDB(this, DBContract.Landing.COLUMN_LANDING_PLATE_NUM, 1, DBContract.Landing.TABLE_LANDING, DBContract.Landing.COLUMN_LANDING_ID) == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.landing_dialog, null);
            builder.setView(dialogView);
            final EditText plateNum = (EditText) dialogView.findViewById(R.id.landing_plate_num);
            final EditText driverName = (EditText) dialogView.findViewById(R.id.landing_driver_name);
            builder.setPositiveButton("Confirm", null); // o
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(final DialogInterface dialog) {
                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(!plateNum.getText().toString().equals("") && !driverName.getText().toString().equals("")){
                                SQLiteDatabase db = dbHandler.getWritableDatabase();
                                ContentValues cv = new ContentValues();
                                cv.put(DBContract.Landing.COLUMN_LANDING_PLATE_NUM, plateNum.getText().toString());
                                cv.put(DBContract.Landing.COLUMN_LANDING_DRIVER, driverName.getText().toString());
                                db.update(DBContract.Landing.TABLE_LANDING, cv, DBContract.Landing.COLUMN_LANDING_ID + "=" + 1, null);
                                refreshLandingDetails();
                                dialog.dismiss();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "Please Complete!", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            });
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.setTitle("Enter landing details");
            dialog.show();
        }
    }

    public void refreshLandingDetails(){
        String plateNum = tripHelper.getStringFromDB(this,
                DBContract.Landing.COLUMN_LANDING_PLATE_NUM,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        String driver = tripHelper.getStringFromDB(this,
                DBContract.Landing.COLUMN_LANDING_DRIVER,
                1,
                DBContract.Landing.TABLE_LANDING,
                DBContract.Landing.COLUMN_LANDING_ID);
        plateNumView.setText("Current Plate: " + plateNum);
        driverNameView.setText("Current Driver: " + driver);
    }

}
