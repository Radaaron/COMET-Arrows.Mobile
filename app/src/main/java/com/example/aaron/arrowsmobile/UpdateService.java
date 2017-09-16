package com.example.aaron.arrowsmobile;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

// non binding service to get updates from server and update db
public class UpdateService extends Service implements OnNetworkSuccessListener{

    NetworkHandler networkHandler;
    ScheduledExecutorService executorService;

    @Override
    public void onCreate() {
        try {
            networkHandler = new NetworkHandler(this);
            executorService = Executors.newScheduledThreadPool(1); // only one thread for sequential updates
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "Service Start");

        // use ScheduledExecutorService to create new thread and also to get updates every 5 min
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // get updates
                Log.e(TAG, "Server Get Start");
                networkHandler.volleyGetUpdateRequest(getApplicationContext());
                Log.e(TAG, "Server Get End");
                // return data
                Log.e(TAG, "Server Post Start");
                networkHandler.volleyPostRequest(getApplicationContext());
                Log.e(TAG, "Server Post End");
            }
        }, 300000, 300000, TimeUnit.MILLISECONDS); // 300000ms = 5min

        // If we get killed, after returning from here, restart
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "Service End");
        executorService.shutdown();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // no binding so it returns null
        return null;
    }

    @Override
    public void onNetworkSuccess(Boolean b) {
        if(b){
            Log.e(TAG, "Update Success");
        }
    }

}
