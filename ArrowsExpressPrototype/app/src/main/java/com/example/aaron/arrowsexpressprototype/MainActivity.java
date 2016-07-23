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
    private Button scanBtn, gpsBtn;
    private String studentID;
    private String area;
    private TextView contentView, areaView, timeView, dateView;
    private Calendar calendar;
    private GPSHandler gpsHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        scanBtn = (Button)findViewById(R.id.scan_button);
        gpsBtn = (Button)findViewById(R.id.gps_button);
        contentView = (TextView)findViewById(R.id.scan_content);
        areaView = (TextView)findViewById(R.id.areaView);
        timeView = (TextView) findViewById(R.id.timeView);
        dateView = (TextView) findViewById(R.id.dateView);
        scanBtn.setOnClickListener(this);
        gpsBtn.setOnClickListener(this);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(requestCode == 0){ // null
            IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
            if (scanningResult != null) {
                studentID = scanningResult.getContents();
                String scanFormat = scanningResult.getFormatName();
                if (!scanFormat.equals("CODE_128")) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            "Invalid barcode type", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    contentView.append("\n " + studentID);
                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "No scan data received!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        else if(requestCode == 1){ // GPSHandler
            Bundle extras = intent.getExtras();
            area = extras.getString("area");
            areaView.append("\n" + area);
        }
    }

    public void onClick(View view){
        if(view.getId() == R.id.gps_button){
            Intent intent = new Intent(this, GPSHandler.class);
            startActivityForResult(intent, 1);
        }
        if(view.getId()== R.id.scan_button){
            IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
            calendar = Calendar.getInstance(); // gets date and time data
            SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
            SimpleDateFormat time = new SimpleDateFormat("HH:mm");// used to store date in month-day-year format
            dateView.append("\n" + date.format(calendar.getTime()));
            timeView.append("\n" + time.format(calendar.getTime()));
            scanIntegrator.initiateScan();
        }
    }

}
