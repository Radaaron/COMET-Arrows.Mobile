package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

// handles the transportation of data from json to sqlite db
public class JSONHandler {

    private DBHandler dbHandler;
    private Context context;
    private String TAG  = JSONHandler.class.getSimpleName();


    JSONHandler(Context context) throws JSONException {
        this.context = context;
        this.dbHandler = new DBHandler(context);
    }

    //populates the database based on jsonObject
    public void populateDatabase(){
        new getArrowsData().execute();
    }

    private class getArrowsData extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context, "JSON Data is downloading..." ,Toast.LENGTH_LONG).show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            HTTPHandler sh = new HTTPHandler();
            // Making a request to url and getting response
            String url = "http://api.androidhive.info/contacts/";
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                    ContentValues cv = new ContentValues();
                    // parse data into the database
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray arrowsArray = jsonObj.getJSONArray("arrowsJSON");
                    JSONArray trips = arrowsArray.getJSONArray(1);
                    JSONArray passengers = arrowsArray.getJSONArray(2);
                    JSONArray drivers = arrowsArray.getJSONArray(3);
                    JSONArray vehicles = arrowsArray.getJSONArray(4);
                    JSONArray tripScheds = arrowsArray.getJSONArray(5);
                    JSONArray routes = arrowsArray.getJSONArray(6);
                    JSONArray lines = arrowsArray.getJSONArray(7);
                    JSONArray routeStops = arrowsArray.getJSONArray(8);
                    JSONArray stops = arrowsArray.getJSONArray(9);
                    JSONArray users = arrowsArray.getJSONArray(10);
                    JSONArray reservations = arrowsArray.getJSONArray(11);

                    //store all trips into database
                    for(int i = 0; i < trips.length(); i++){
                        JSONObject trip = trips.getJSONObject(i);
                        cv.put(DBContract.Trip.COLUMN_TRIP_ID, trip.getInt("tripID"));
                        cv.put(DBContract.Trip.COLUMN_REMARKS, trip.getString("remarks"));
                        cv.put(DBContract.Trip.COLUMN_TRIP_DATE, trip.getString("tripDate"));
                        cv.put(DBContract.Trip.COLUMN_DEP_TIME, trip.getString("depTime"));
                        cv.put(DBContract.Trip.COLUMN_ARRIVAL_TIME, trip.getString("arrivalTime"));
                        cv.put(DBContract.Trip.COLUMN_DURATION, trip.getDouble("duration"));
                        cv.put(DBContract.Trip.COLUMN_IS_SPECIAL, trip.getBoolean("isSpecial"));
                        cv.put(DBContract.Trip.COLUMN_SP_NUM_PASS, trip.getInt("spNumPass"));
                        cv.put(DBContract.Trip.COLUMN_PURPOSE, trip.getString("purpose"));
                        cv.put(DBContract.Trip.COLUMN_TRIP_VEHICLE, trip.getString("tripVehicle"));
                        cv.put(DBContract.Trip.COLUMN_TRIP_DRIVER, trip.getInt("tripDriver"));
                        cv.put(DBContract.Trip.COLUMN_TRIP_TRIP_SCHED, trip.getInt("tripTripSched"));
                        db.insert(DBContract.Trip.TABLE_TRIP, null, cv);
                    }

                    //store all passengers into database
                    for(int i = 0; i < passengers.length(); i++){
                        JSONObject passenger = passengers.getJSONObject(i);
                        cv.put(DBContract.Passenger.COLUMN_PASSENGER_ID, passenger.getInt("passengerID"));
                        cv.put(DBContract.Passenger.COLUMN_FEEDBACK_ON, passenger.getString("feedbackOn"));
                        cv.put(DBContract.Passenger.COLUMN_FEEDBACK, passenger.getInt("feedback"));
                        cv.put(DBContract.Passenger.COLUMN_TAP_IN, passenger.getString("tapIn"));
                        cv.put(DBContract.Passenger.COLUMN_TAP_OUT, passenger.getString("tapOut"));
                        cv.put(DBContract.Passenger.COLUMN_DISEMBARKATION_PT, passenger.getString("disembarkationPt"));
                        cv.put(DBContract.Passenger.COLUMN_DESTINATION, passenger.getString("destination"));
                        cv.put(DBContract.Passenger.COLUMN_PASSENGER_RESERVATION, passenger.getInt("passengerReservationNum"));
                        cv.put(DBContract.Passenger.COLUMN_PASSENGER_VEHICLE, passenger.getString("passengerVehicle"));
                        cv.put(DBContract.Passenger.COLUMN_PASSENGER_DRIVER, passenger.getInt("passengerDriver"));
                        db.insert(DBContract.Passenger.TABLE_PASSENGER, null, cv);
                    }

                    //store all drivers into database
                    for(int i = 0; i < drivers.length(); i++){
                        JSONObject driver = drivers.getJSONObject(i);
                        cv.put(DBContract.Driver.COLUMN_DRIVER_ID, driver.getInt("driverID"));
                        cv.put(DBContract.Driver.COLUMN_LAST_NAME, driver.getString("lastName"));
                        cv.put(DBContract.Driver.COLUMN_FIRST_NAME, driver.getString("firstName"));
                        cv.put(DBContract.Driver.COLUMN_NICKNAME, driver.getString("nickName"));
                        db.insert(DBContract.Driver.TABLE_DRIVER, null, cv);
                    }

                    //store all vehicles into database
                    for(int i = 0; i < vehicles.length(); i++){
                        JSONObject vehicle = vehicles.getJSONObject(i);
                        cv.put(DBContract.Vehicle.COLUMN_VEHICLE_ID, vehicle.getString("vehicleID"));
                        cv.put(DBContract.Vehicle.COLUMN_VEHICLE_TYPE, vehicle.getString("vehicleType"));
                        cv.put(DBContract.Vehicle.COLUMN_CAPACITY, vehicle.getInt("capacity"));
                        cv.put(DBContract.Vehicle.COLUMN_IMAGE, vehicle.getString("image"));
                        cv.put(DBContract.Vehicle.COLUMN_PLATE_NUM, vehicle.getString("plateNum"));
                        cv.put(DBContract.Vehicle.COLUMN_MODEL, vehicle.getString("model"));
                        cv.put(DBContract.Vehicle.COLUMN_BRAND, vehicle.getString("brand"));
                        db.insert(DBContract.Vehicle.TABLE_VEHICLE, null, cv);
                    }

                    //store all tripScheds into database
                    for(int i = 0; i < tripScheds.length(); i++){
                        JSONObject tripSched = tripScheds.getJSONObject(i);
                        cv.put(DBContract.TripSched.COLUMN_TRIP_SCHED_ID, tripSched.getInt("tripSchedID"));
                        cv.put(DBContract.TripSched.COLUMN_TRIP_NUM, tripSched.getString("tripNum"));
                        cv.put(DBContract.TripSched.COLUMN_DEP_TIME, tripSched.getString("depTime"));
                        cv.put(DBContract.TripSched.COLUMN_TRIP_SCHED_ROUTE, tripSched.getInt("tripSchedRoute"));
                        db.insert(DBContract.TripSched.TABLE_TRIP_SCHED, null, cv);
                    }

                    //store all routes into database
                    for(int i = 0; i < routes.length(); i++){
                        JSONObject route = routes.getJSONObject(i);
                        cv.put(DBContract.Route.COLUMN_ROUTE_ID, route.getInt("routeID"));
                        cv.put(DBContract.Route.COLUMN_ROUTE_ORIGIN, route.getString("routeOrigin"));
                        cv.put(DBContract.Route.COLUMN_ROUTE_NAME, route.getString("routeName"));
                        cv.put(DBContract.Route.COLUMN_ROUTE_DESCRIPTION, route.getString("routeDescription"));
                        cv.put(DBContract.Route.COLUMN_ROUTE_LINE, route.getInt("routeLine"));
                        db.insert(DBContract.Route.TABLE_ROUTE, null, cv);
                    }

                    //store all lines into database
                    for(int i = 0; i < lines.length(); i++){
                        JSONObject line = lines.getJSONObject(i);
                        cv.put(DBContract.Line.COLUMN_LINE_NUM, line.getInt("lineNum"));
                        cv.put(DBContract.Line.COLUMN_LINE_NAME, line.getString("lineName"));
                        db.insert(DBContract.Line.TABLE_LINE, null, cv);
                    }

                    //store all routeStops into database
                    for(int i = 0; i < routeStops.length(); i++){
                        JSONObject routeStop = routeStops.getJSONObject(i);
                        cv.put(DBContract.RouteStop.COLUMN_STOP_NUM, routeStop.getString("stopNum"));
                        cv.put(DBContract.RouteStop.COLUMN_ORDER, routeStop.getInt("order"));
                        cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_STOP, routeStop.getInt("routeStopRoute"));
                        cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_ROUTE, routeStop.getInt("routeStopStop"));
                        db.insert(DBContract.RouteStop.TABLE_ROUTE_STOP, null, cv);
                    }

                    //store all stops into database
                    for(int i = 0; i < stops.length(); i++){
                        JSONObject stop = stops.getJSONObject(i);
                        cv.put(DBContract.Stop.COLUMN_STOP_ID, stop.getInt("stopID"));
                        cv.put(DBContract.Stop.COLUMN_STOP_NAME, stop.getString("stopName"));
                        cv.put(DBContract.Stop.COLUMN_LATITUDE, stop.getDouble("latitude"));
                        cv.put(DBContract.Stop.COLUMN_LONGITUDE, stop.getDouble("longitude"));
                        db.insert(DBContract.Stop.TABLE_STOP, null, cv);
                    }

                    //store all users into database
                    for(int i = 0; i < users.length(); i++){
                        JSONObject user = users.getJSONObject(i);
                        cv = new ContentValues();
                        cv.put(DBContract.User.COLUMN_ID_NUM, user.getInt("idNum"));
                        cv.put(DBContract.User.COLUMN_NAME, user.getString("name"));
                        cv.put(DBContract.User.COLUMN_EMAIL, user.getString("email"));
                        cv.put(DBContract.User.COLUMN_EMERGENCY_CONTACT, user.getString("emergencyContact"));
                        cv.put(DBContract.User.COLUMN_EMERGENCY_CONTACT_NUM, user.getString("emergencyContactNum"));
                        cv.put(DBContract.User.COLUMN_IS_ADMIN, user.getBoolean("isAdmin"));
                        cv.put(DBContract.User.COLUMN_ADMIN_PASSWORD, user.getString("adminPassword"));
                        cv.put(DBContract.User.COLUMN_AP_PRIORITY_ID, user.getString("apPriorityID"));
                        db.insert(DBContract.User.TABLE_USER, null, cv);
                    }

                    //store all reservations into database
                    for(int i = 0; i < reservations.length(); i++){
                        JSONObject reservation = reservations.getJSONObject(i);
                        cv = new ContentValues();
                        cv.put(DBContract.Reservation.COLUMN_RESERVATION_NUM, reservation.getInt("reservationNum"));
                        cv.put(DBContract.Reservation.COLUMN_TIMESTAMP, reservation.getInt("timeStamp"));
                        cv.put(DBContract.Reservation.COLUMN_DESTINATION, reservation.getInt("destination"));
                        cv.put(DBContract.Reservation.COLUMN_REMARK, reservation.getInt("remark"));
                        cv.put(DBContract.Reservation.COLUMN_RESERVATION_TRIP, reservation.getInt("reservationTrip"));
                        cv.put(DBContract.Reservation.COLUMN_RESERVATION_ROUTE_STOP, reservation.getString("reservationRouteStop"));
                        cv.put(DBContract.Reservation.COLUMN_RESERVATION_USER, reservation.getInt("reservationUser"));
                        db.insert(DBContract.Reservation.TABLE_RESERVATION, null, cv);
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
            }
            return null;
        }
    }

}
