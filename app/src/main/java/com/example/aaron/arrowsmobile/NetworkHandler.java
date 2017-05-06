package com.example.aaron.arrowsmobile;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
                        jsonParser.jsonToDb(ctx);
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

    public void volleyGetUpdateRequest(Context context){
        final Context ctx = context;
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONHandler jsonParser = new JSONHandler(response);
                        jsonParser.jsonUpdateToDb(ctx);
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

    public void volleyPostRequest(Context context){
        final Context ctx = context;
        JSONHandler jsonHandler = new JSONHandler();
        JSONObject arrowsObject = null;
        try {
            arrowsObject = jsonHandler.dbToJson(context);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = arrowsObject.toString();
        Log.e(TAG, requestBody);
        StringRequest stringRequest = new StringRequest(1, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e(TAG, "Result: " + response);
                onNetworkSuccessListener.onNetworkSuccess(true);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "JSON Post Error");
                onNetworkSuccessListener.onNetworkSuccess(false);
            }
        }) {
            @Override
            public String getBodyContentType() {
                return String.format("application/json; charset=utf-8");
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                            requestBody, "utf-8");
                    return null;
                }
            }
        };
        // Access the RequestQueue through your singleton class.
        VolleySingleton.getInstance(ctx).addToRequestQueue(stringRequest);
    }
}
