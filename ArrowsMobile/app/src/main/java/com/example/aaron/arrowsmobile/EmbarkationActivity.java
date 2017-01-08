package com.example.aaron.arrowsmobile;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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

public class EmbarkationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener, View.OnClickListener {

    DBHandler dbHandler;
    Trip selectedTrip;
    Fragment currentFragment;
    String chanceDestination = null;

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
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

        // get access to DB and retrieve trip details
        dbHandler = new DBHandler(this);
        selectedTrip = getIntent().getParcelableExtra("selectedTrip");
        dateView.setText(dateFormat.format(selectedTrip.getTripDate().getTime()));
        timeView.setText(timeFormat.format(selectedTrip.getDepTime().getTime()));
        routeView.setText(selectedTrip.getTripSched().getRoute().getRouteName());

        // initially commit passenger manifest fragment
        Bundle bundle = new Bundle();
        bundle.putParcelable("selectedTrip", selectedTrip);
        currentFragment = new PassengerManifestFragment();
        currentFragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
        setTitle("Passenger Manifest");
        fragmentTransaction.commit();
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
            Bundle bundle = new Bundle();
            bundle.putParcelable("selectedTrip", selectedTrip);
            currentFragment = new PassengerManifestFragment();
            currentFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
            setTitle("Passenger Manifest");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_chance_passengers && !(currentFragment instanceof ChancePassengersFragment)) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("selectedTrip", selectedTrip);
            currentFragment = new ChancePassengersFragment();
            currentFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
            setTitle("Chance Passengers");
            fragmentTransaction.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.embarkation_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            // no change, just refresh whatever the current fragment is
            if(currentFragment instanceof PassengerManifestFragment){
                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedTrip", selectedTrip);
                currentFragment = new PassengerManifestFragment();
                currentFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
                setTitle("Passenger Manifest");
                fragmentTransaction.commitAllowingStateLoss(); // needed because its commiting after savedInstanceState
            } else if(currentFragment instanceof ChancePassengersFragment){
                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedTrip", selectedTrip);
                currentFragment = new ChancePassengersFragment();
                currentFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
                setTitle("Chance Passengers");
                fragmentTransaction.commitAllowingStateLoss();
            }
        } else if(resultCode == RESULT_OK){
            final String id = data.getStringExtra("id");
            // check whether id is in manifest or is chance passenger
            for(int i = 0; i < selectedTrip.getPassengerCount(); i++){
                if(Integer.toString(selectedTrip.getPassenger(i).getPassengerID()).equals(id)){
                    // set existing passenger tap in
                    Calendar cal = Calendar.getInstance();
                    selectedTrip.getPassenger(i).setTapIn(cal);
                    // refresh passenger manifest
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("selectedTrip", selectedTrip);
                    currentFragment = new PassengerManifestFragment();
                    currentFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
                    setTitle("Passenger Manifest");
                    fragmentTransaction.commitAllowingStateLoss();
                } else{
                    // get chance passenger destination
                    final String[] destinations = {"Caltex", "STC Campus"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Choose Chance Passenger Destination");
                    builder.setItems(destinations, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            chanceDestination = destinations[which];
                            // add new passenger
                            Calendar cal = Calendar.getInstance();
                            Passenger chancePass = new Passenger(Integer.parseInt(id), "", 0, cal, null, selectedTrip.getPassenger(0).getDisembarkationPt(), chanceDestination, true);
                            selectedTrip.addToPassengerList(chancePass);
                            // refresh chance passengers
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("selectedTrip", selectedTrip);
                            currentFragment = new ChancePassengersFragment();
                            currentFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
                            setTitle("Chance Passengers");
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            chanceDestination = null;
                            // refresh chance passengers
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("selectedTrip", selectedTrip);
                            currentFragment = new ChancePassengersFragment();
                            currentFragment.setArguments(bundle);
                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                            fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
                            setTitle("Chance Passengers");
                            fragmentTransaction.commitAllowingStateLoss();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        }
    }

    public int remainingSpace(Trip selectedTrip){
        int free = selectedTrip.getVehicle().getCapacity();
        for(int i = 0; i < selectedTrip.getPassengerCount(); i++){
            if(selectedTrip.getPassenger(i).getTapIn() != null){
                free--;
            }
        }
        return free;
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
                        currentFragment = new ChancePassengersFragment();
                        currentFragment.setArguments(bundle);
                        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.embarkation_fragment_container, currentFragment);
                        setTitle("Chance Passengers");
                        fragmentTransaction.commitAllowingStateLoss();
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
