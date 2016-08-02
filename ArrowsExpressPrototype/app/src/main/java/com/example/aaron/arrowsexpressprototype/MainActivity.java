package com.example.aaron.arrowsexpressprototype;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends BaseActivity implements OnClickListener{

    public static Activity activity;
    private Button scanBtn, gpsBtn, endBtn;
    private ImageButton manScan, manGPS;
    private String studentID;
    private String area;
    private TextView areaView, timeView, dateView;
    private ArrayList<String> idList;
    private ArrayList<ServiceEvent> eventsList;
    ArrayAdapter<String> idListAdapter;
    private ListView idListView;
    private Calendar calendar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        createTripList();
        scanBtn = (Button)findViewById(R.id.scanID_button);
        gpsBtn = (Button)findViewById(R.id.gps_button);
        endBtn = (Button)findViewById(R.id.endTrip_button);
        manScan = (ImageButton)findViewById(R.id.manScan_button);
        manGPS = (ImageButton)findViewById(R.id.manGPS_button);
        areaView = (TextView)findViewById(R.id.areaView);
        timeView = (TextView) findViewById(R.id.timeView);
        dateView = (TextView) findViewById(R.id.dateView);
        eventsList =  new ArrayList<ServiceEvent>();
        idList = new ArrayList<String>();
        idListView = (ListView)findViewById(R.id.idListView);
        idListAdapter = new ArrayAdapter<String>(this, R.layout.custom_list_item, idList);
        idListView.setAdapter(idListAdapter);
        scanBtn.setOnClickListener(this);
        gpsBtn.setOnClickListener(this);
        endBtn.setOnClickListener(this);
        manGPS.setOnClickListener(this);
        manScan.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 1){ // GPSHandler
            Bundle extras = intent.getExtras();
            area = extras.getString("area");
            if(area.equals("off")){
                Toast toast = Toast.makeText(getApplicationContext(),
                        "GPS IS TURNED OFF", Toast.LENGTH_SHORT);
                toast.show();
            }
            else if(area != null){
                areaView.append("" + area);
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "NOT IN SERVICE STOP", Toast.LENGTH_SHORT);
                toast.show();
            }
        } else {
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                studentID = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if (!scanFormat.equals("CODE_128")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "INVALID BARCODE TYPE", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {
                    idList.add(studentID);
                    idListAdapter.notifyDataSetChanged();
                    addToTrip(area, calendar, studentID);
                }
            }
            else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "NO SCAN DATA RECEIVED", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void addToTrip(String area, Calendar calendar, String studentID){
        ServiceEvent temp = new ServiceEvent(area, calendar, studentID);
        eventsList.add(temp);
    }

    public void resetAll(){
        areaView.setText("Service Stop: ");
        dateView.setText("Date: ");
        timeView.setText("Time: ");
    }

    public void onClick(View view){
        if(view.getId() == R.id.gps_button){
            resetAll();
            Intent intent = new Intent(this, GPSHandler.class);
            startActivityForResult(intent, 1);
            calendar = Calendar.getInstance(); // gets date and time data
            SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");// used to store date in month-day-year format
            dateView.append("" + date.format(calendar.getTime()));
            timeView.append("" + time.format(calendar.getTime()));
        }
        else if(view.getId()== R.id.scanID_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
            scanIntegrator.initiateScan();
        }
        else if(view.getId()== R.id.manGPS_button){ // uses Alert Dialogue to prompt user for input
            View inputView = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.user_input, null);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setView(inputView);
            final EditText userInput = (EditText) inputView.findViewById(R.id.userInput);

            alertBuilder.setCancelable(true)
                    .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            area = userInput.getText().toString();
                            areaView.append("" + area);
                        }
                    });
            Dialog dialog = alertBuilder.create();
            dialog.show();
        }
        else if(view.getId()== R.id.manScan_button){
            View inputView = (LayoutInflater.from(MainActivity.this)).inflate(R.layout.user_input, null);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
            alertBuilder.setView(inputView);
            final EditText userInput = (EditText) inputView.findViewById(R.id.userInput);

            alertBuilder.setCancelable(true)
                    .setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            studentID = userInput.getText().toString();
                            idList.add(studentID);
                            idListAdapter.notifyDataSetChanged();
                            addToTrip(area, calendar, studentID);
                        }
                    });
            Dialog dialog = alertBuilder.create();
            dialog.show();
        }
        else if(view.getId()== R.id.endTrip_button){
            Trip temp = new Trip(eventsList);
            addTrip(temp);
            resetAll();
            idList.clear();
            idListAdapter.notifyDataSetChanged();
            Toast toast = Toast.makeText(getApplicationContext(),
                    "TRIP RECORDED", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
