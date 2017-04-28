package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static android.content.ContentValues.TAG;

public class JSONParser {
    private JSONObject json;

    public JSONParser(JSONObject json){
        this.json = json;
    }

    public void parseJSON(Context context) {
        try {
            DBHandler dbHandler = new DBHandler(context);
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            ContentValues cv;
            // parse data into the database
            JSONObject jsonObj = this.json;
            JSONObject arrowsObject = jsonObj.optJSONObject("arrowsJSON");
            JSONArray trips = arrowsObject.optJSONArray("trips");
            JSONArray passengers = arrowsObject.optJSONArray("passengers");
            JSONArray drivers = arrowsObject.optJSONArray("drivers");
            JSONArray vehicles = arrowsObject.optJSONArray("vehicles");
            JSONArray tripScheds = arrowsObject.optJSONArray("tripScheds");
            JSONArray routes = arrowsObject.optJSONArray("routes");
            JSONArray lines = arrowsObject.optJSONArray("lines");
            JSONArray routeStops = arrowsObject.optJSONArray("routeStops");
            JSONArray stops = arrowsObject.optJSONArray("stops");
            JSONArray users = arrowsObject.optJSONArray("users");
            JSONArray reservations = arrowsObject.optJSONArray("reservations");

            //store all trips into database
            for(int i = 0; i < trips.length(); i++){
                JSONObject trip = trips.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Trip.COLUMN_TRIP_ID, trip.optInt("tripId"));
                cv.put(DBContract.Trip.COLUMN_REMARKS, trip.optString("remarks"));
                cv.put(DBContract.Trip.COLUMN_TRIP_DATE, trip.optString("tripDate"));
                cv.put(DBContract.Trip.COLUMN_DEP_TIME, trip.optString("depTime"));
                cv.put(DBContract.Trip.COLUMN_ARRIVAL_TIME, trip.optString("arrivalTime"));
                cv.put(DBContract.Trip.COLUMN_DURATION, trip.optDouble("duration"));
                cv.put(DBContract.Trip.COLUMN_IS_SPECIAL, trip.optBoolean("isSpecial"));
                cv.put(DBContract.Trip.COLUMN_SP_NUM_PASS, trip.optInt("spNumPass"));
                cv.put(DBContract.Trip.COLUMN_PURPOSE, trip.optString("purpose"));
                cv.put(DBContract.Trip.COLUMN_TRIP_VEHICLE, trip.optString("vehicleId"));
                cv.put(DBContract.Trip.COLUMN_TRIP_DRIVER, trip.optInt("driverId"));
                cv.put(DBContract.Trip.COLUMN_TRIP_TRIP_SCHED, trip.optInt("tripSchedId"));
                db.insert(DBContract.Trip.TABLE_TRIP, null, cv);
            }

            //store all passengers into database
            for(int i = 0; i < passengers.length(); i++){
                JSONObject passenger = passengers.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Passenger.COLUMN_PASSENGER_ID, passenger.optInt("passengerId"));
                cv.put(DBContract.Passenger.COLUMN_FEEDBACK_ON, passenger.optString("feedbackOn"));
                cv.put(DBContract.Passenger.COLUMN_FEEDBACK, passenger.optInt("feedback"));
                cv.put(DBContract.Passenger.COLUMN_TAP_IN, passenger.optString("tapIn"));
                cv.put(DBContract.Passenger.COLUMN_TAP_OUT, passenger.optString("tapOut"));
                cv.put(DBContract.Passenger.COLUMN_DISEMBARKATION_PT, passenger.optString("disembarkationPt"));
                cv.put(DBContract.Passenger.COLUMN_DESTINATION, passenger.optString("destination"));
                cv.put(DBContract.Passenger.COLUMN_PASSENGER_RESERVATION, passenger.optInt("passengerReservationNum"));
                cv.put(DBContract.Passenger.COLUMN_PASSENGER_VEHICLE, passenger.optString("passengerVehicle"));
                cv.put(DBContract.Passenger.COLUMN_PASSENGER_DRIVER, passenger.optInt("passengerDriver"));
                db.insert(DBContract.Passenger.TABLE_PASSENGER, null, cv);
            }

            //store all drivers into database
            for(int i = 0; i < drivers.length(); i++){
                JSONObject driver = drivers.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Driver.COLUMN_DRIVER_ID, driver.optInt("driverId"));
                cv.put(DBContract.Driver.COLUMN_LAST_NAME, driver.optString("lastName"));
                cv.put(DBContract.Driver.COLUMN_FIRST_NAME, driver.optString("firstName"));
                cv.put(DBContract.Driver.COLUMN_NICKNAME, driver.optString("nickname"));
                db.insert(DBContract.Driver.TABLE_DRIVER, null, cv);
            }

            //store all vehicles into database
            for(int i = 0; i < vehicles.length(); i++){
                JSONObject vehicle = vehicles.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Vehicle.COLUMN_VEHICLE_ID, vehicle.optString("vehicleId"));
                cv.put(DBContract.Vehicle.COLUMN_VEHICLE_TYPE, vehicle.optString("vehicleType"));
                cv.put(DBContract.Vehicle.COLUMN_CAPACITY, vehicle.optInt("capacity"));
                cv.put(DBContract.Vehicle.COLUMN_IMAGE, vehicle.optString("image"));
                cv.put(DBContract.Vehicle.COLUMN_PLATE_NUM, vehicle.optString("plateNum"));
                cv.put(DBContract.Vehicle.COLUMN_MODEL, vehicle.optString("model"));
                cv.put(DBContract.Vehicle.COLUMN_BRAND, vehicle.optString("brand"));
                db.insert(DBContract.Vehicle.TABLE_VEHICLE, null, cv);
            }

            //store all tripScheds into database
            for(int i = 0; i < tripScheds.length(); i++){
                JSONObject tripSched = tripScheds.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.TripSched.COLUMN_TRIP_SCHED_ID, tripSched.optInt("tripSchedId"));
                cv.put(DBContract.TripSched.COLUMN_TRIP_NUM, tripSched.optString("tripNum"));
                cv.put(DBContract.TripSched.COLUMN_DEP_TIME, tripSched.optString("depTime"));
                cv.put(DBContract.TripSched.COLUMN_TRIP_SCHED_ROUTE, tripSched.optInt("routeId"));
                db.insert(DBContract.TripSched.TABLE_TRIP_SCHED, null, cv);
            }

            //store all routes into database
            for(int i = 0; i < routes.length(); i++){
                JSONObject route = routes.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Route.COLUMN_ROUTE_ID, route.optInt("routeId"));
                cv.put(DBContract.Route.COLUMN_ROUTE_ORIGIN, route.optString("origin"));
                cv.put(DBContract.Route.COLUMN_ROUTE_DESTINATION, route.optString("destination"));
                cv.put(DBContract.Route.COLUMN_ROUTE_DESCRIPTION, route.optString("routeDescription"));
                cv.put(DBContract.Route.COLUMN_ROUTE_LINE, route.optInt("lineNum"));
                db.insert(DBContract.Route.TABLE_ROUTE, null, cv);
            }

            //store all lines into database
            for(int i = 0; i < lines.length(); i++){
                JSONObject line = lines.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Line.COLUMN_LINE_NUM, line.optInt("lineNum"));
                cv.put(DBContract.Line.COLUMN_LINE_NAME, line.optString("lineName"));
                db.insert(DBContract.Line.TABLE_LINE, null, cv);
            }

            //store all routeStops into database
            for(int i = 0; i < routeStops.length(); i++){
                JSONObject routeStop = routeStops.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.RouteStop.COLUMN_STOP_NUM, routeStop.optString("stopNum"));
                cv.put(DBContract.RouteStop.COLUMN_ORDER, routeStop.optInt("order"));
                cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_STOP, routeStop.optInt("routeId"));
                cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_ROUTE, routeStop.optInt("stopID"));
                db.insert(DBContract.RouteStop.TABLE_ROUTE_STOP, null, cv);
            }

            //store all stops into database
            for(int i = 0; i < stops.length(); i++){
                JSONObject stop = stops.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Stop.COLUMN_STOP_ID, stop.optInt("stopID"));
                cv.put(DBContract.Stop.COLUMN_STOP_NAME, stop.optString("stopName"));
                cv.put(DBContract.Stop.COLUMN_LATITUDE, stop.optDouble("latitude"));
                cv.put(DBContract.Stop.COLUMN_LONGITUDE, stop.optDouble("longitude"));
                db.insert(DBContract.Stop.TABLE_STOP, null, cv);
            }

            //store all users into database
            for(int i = 0; i < users.length(); i++){
                JSONObject user = users.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.User.COLUMN_ID_NUM, user.optInt("idNum"));
                cv.put(DBContract.User.COLUMN_NAME, user.optString("name"));
                cv.put(DBContract.User.COLUMN_EMAIL, user.optString("email"));
                cv.put(DBContract.User.COLUMN_EMERGENCY_CONTACT, user.optString("emergencyContact"));
                cv.put(DBContract.User.COLUMN_EMERGENCY_CONTACT_NUM, user.optString("emergencyContactNum"));
                cv.put(DBContract.User.COLUMN_IS_ADMIN, user.optBoolean("isAdmin"));
                cv.put(DBContract.User.COLUMN_ADMIN_PASSWORD, user.optString("adminPassword"));
                cv.put(DBContract.User.COLUMN_AP_PRIORITY_ID, user.optString("priorityType"));
                db.insert(DBContract.User.TABLE_USER, null, cv);
            }

            //store all reservations into database
            for(int i = 0; i < reservations.length(); i++){
                JSONObject reservation = reservations.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Reservation.COLUMN_RESERVATION_NUM, reservation.optInt("reservationNum"));
                cv.put(DBContract.Reservation.COLUMN_TIMESTAMP, reservation.optInt("timestamp"));
                cv.put(DBContract.Reservation.COLUMN_DESTINATION, reservation.optInt("destination"));
                cv.put(DBContract.Reservation.COLUMN_REMARK, reservation.optInt("remark"));
                cv.put(DBContract.Reservation.COLUMN_RESERVATION_TRIP, reservation.optInt("tripId"));
                cv.put(DBContract.Reservation.COLUMN_RESERVATION_ROUTE_STOP, reservation.optString("stopNum"));
                cv.put(DBContract.Reservation.COLUMN_RESERVATION_USER, reservation.optInt("idNum"));
                db.insert(DBContract.Reservation.TABLE_RESERVATION, null, cv);
            }
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

}
