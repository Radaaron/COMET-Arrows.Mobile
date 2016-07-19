package com.example.aaron.arrowsexpressprototype;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements OnClickListener{

    public static Activity activity;
    private Button scanBtn;
    private String studentID;
    private double longitude, latitude;
    private TextView formatView, contentView, longitudeView, latitudeView, timeView, dateView;
    private Calendar calendar;
    private GPSHandler gpsHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        scanBtn = (Button)findViewById(R.id.scan_button);
        formatView = (TextView)findViewById(R.id.scan_format);
        contentView = (TextView)findViewById(R.id.scan_content);
        longitudeView = (TextView) findViewById(R.id.longitudeView);
        latitudeView = (TextView) findViewById(R.id.latitudeView);
        timeView = (TextView) findViewById(R.id.timeView);
        dateView = (TextView) findViewById(R.id.dateView);
        gpsHandler = new GPSHandler (getSystemService(LOCATION_SERVICE));
        scanBtn.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanningResult != null) {
            studentID = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();
            if (!scanFormat.equals("CODE_128")) {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Invalid barcode type", Toast.LENGTH_SHORT);
                toast.show();
            } else {
                formatView.setText("FORMAT: " + scanFormat);
                contentView.setText("CONTENT: " + studentID);
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void onClick(View view){
        if(view.getId()== R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(this);
            gpsHandler.getGPS();
            longitude = gpsHandler.getLongitude();
            latitude = gpsHandler.getLatitude();
            if(longitude != 0.0 && latitude != 0.0) {
                longitudeView.append("\n " + longitude);
                latitudeView.append("\n " + latitude);
            }
            calendar = Calendar.getInstance(); // gets date and time data
            SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");// used to store date in month-day-year format
            dateView.append("\n " + date.format(calendar.getTime()));
            timeView.append("\n " + time.format(calendar.getTime()));
            if(view.getId()==R.id.scan_button){
                scanIntegrator.initiateScan();
            }
            scanIntegrator.initiateScan();
        }
    }

}
