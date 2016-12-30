package com.example.aaron.arrowsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // build database
        dbHandler = new DBHandler(this);

        // start landing process
        Intent intent = new Intent(this, LandingActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            // start embarkation
            Intent intent = new Intent(this, EmbarkationActivity.class);
            startActivityForResult(intent, 1);
        }
    }
}
