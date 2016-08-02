package com.example.aaron.arrowsexpressprototype;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class StorageHandler extends BaseActivity {

    ArrayList<Trip> tripListInstance;
    ListView tripsListView;
    ArrayAdapter<Trip> tripListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        tripListInstance = getTripList();
        tripsListView = (ListView)findViewById(R.id.tripsListView);
        tripListAdapter = new ArrayAdapter<Trip>(this, R.layout.custom_list_item, tripListInstance);
        tripsListView.setAdapter(tripListAdapter);
    }

}
