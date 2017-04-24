package com.example.aaron.arrowsmobile;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import static com.example.aaron.arrowsmobile.R.id.plateNumSpinner;

public class LandingInputActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    KeyHandler keyHandler;
    DBHandler dbHandler;
    String plate, driver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_input);
        keyHandler = new KeyHandler();
        dbHandler = new DBHandler(this);
        Spinner plateNumSpinner = (Spinner) findViewById(R.id.plateNumSpinner);
        Spinner driverSpinner = (Spinner) findViewById(R.id.driverSpinner);
        Button startTripSelection = (Button) findViewById(R.id.start_trip_selection_button);
        plateNumSpinner.setOnItemSelectedListener(this);
        driverSpinner.setOnItemSelectedListener(this);
        startTripSelection.setOnClickListener(this);
        ArrayList<String> plateItems = keyHandler.getStringArrayListFromDB(this, DBContract.Vehicle.COLUMN_PLATE_NUM, DBContract.Vehicle.TABLE_VEHICLE);
        ArrayList<String> driverFirstNames = keyHandler.getStringArrayListFromDB(this, DBContract.Driver.COLUMN_FIRST_NAME, DBContract.Driver.TABLE_DRIVER);
        ArrayList<String> driverLastNames = keyHandler.getStringArrayListFromDB(this, DBContract.Driver.COLUMN_LAST_NAME, DBContract.Driver.TABLE_DRIVER);
        ArrayList<String> driverItems = new ArrayList<>();
        for(int i = 0; i < driverFirstNames.size(); i++){
            driverItems.add(driverFirstNames.get(i) + " " + driverLastNames.get(i));
        }
        ArrayAdapter<String> plateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, plateItems);
        ArrayAdapter<String> driverAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, driverItems);
        plateAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        driverAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        plateNumSpinner.setAdapter(plateAdapter);
        driverSpinner.setAdapter(driverAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // check parent id
        switch(parent.getId()){
            case plateNumSpinner :
                plate = parent.getItemAtPosition(position).toString();
                break;
            case R.id.driverSpinner :
                driver = parent.getItemAtPosition(position).toString();
                break;
        }
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // nothing
    }

    @Override
    public void onClick(View v) {
        if (v.getId()== R.id.start_trip_selection_button) {
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(DBContract.Landing.COLUMN_LANDING_PLATE_NUM, plate);
            cv.put(DBContract.Landing.COLUMN_LANDING_DRIVER, driver);
            db.update(DBContract.Landing.TABLE_LANDING, cv, DBContract.Landing.COLUMN_LANDING_ID + "=" + 1, null);
            Intent intent = new Intent();
            intent.putExtra("next", "Landing");
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    }
    @Override
    public void onBackPressed() {
        // do nothing
    }
}
