package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.ContentValues.TAG;

// handles the transportation of data from json to sqlite db
public class NetworkHandler {
    String url = "http://frozen-escarpment-35603.herokuapp.com/json";
    OnNetworkSuccessListener onNetworkSuccessListener;

    NetworkHandler(OnNetworkSuccessListener listener) throws JSONException {
        onNetworkSuccessListener = listener;
    }

    public void volleyGetRequest(Context context){
        final Context ctx = context;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONHandler jsonParser = new JSONHandler(response);
                        jsonParser.parseJSON(ctx);
                        onNetworkSuccessListener.onNetworkSuccess(true);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "JSON Get Error");
                        onNetworkSuccessListener.onNetworkSuccess(false);
                    }
                });
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsObjRequest);
    }
}
