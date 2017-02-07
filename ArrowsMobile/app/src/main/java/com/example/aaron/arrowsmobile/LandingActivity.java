package com.example.aaron.arrowsmobile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class LandingActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener{

    DBHandler dbHandler;
    ArrayList<KeyHandler> tripList;
    TextView dateView;
    Calendar calendar;
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
        dateView = (TextView) navigationView.getHeaderView(0).findViewById(R.id.dateView);
        calendar = Calendar.getInstance(); // gets date
        SimpleDateFormat date = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        dateView.setText("" + date.format(calendar.getTime())); // displays date
        setTitle("" + date.format(calendar.getTime()));

        // retrieve data for recycler view
        dbHandler = new DBHandler(this);
        tripList = dbHandler.getTripKeyHolderList();

        // initially commit all trips fragment
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("tripList", tripList);
        currentFragment = new AllTripsFragment();
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
        } else if(currentFragment instanceof AllTripsFragment || currentFragment instanceof PendingTripsFragment || currentFragment instanceof CompletedTripsFragment){
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
        if (id == R.id.nav_all_trips && !(currentFragment instanceof AllTripsFragment)) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("tripList", tripList);
            currentFragment = new AllTripsFragment();
            currentFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.landing_fragment_container, currentFragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_pending_trips && !(currentFragment instanceof PendingTripsFragment)) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("tripList", tripList);
            currentFragment = new PendingTripsFragment();
            currentFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.landing_fragment_container, currentFragment);
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_completed_trips && !(currentFragment instanceof CompletedTripsFragment)) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("tripList", tripList);
            currentFragment = new CompletedTripsFragment();
            currentFragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.landing_fragment_container, currentFragment);
            fragmentTransaction.commit();
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

}
