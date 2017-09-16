package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

// handles the json parse into db and and db to json
public class JSONHandler {

    private JSONObject json;

    public JSONHandler(JSONObject json){
        this.json = json;
    }

    public JSONHandler(){}

    public void jsonToDb(Context context) {
        try {
            DBHandler dbHandler = new DBHandler(context);
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            ContentValues cv;
            // parse data into the database
            JSONObject jsonObj = this.json;
            JSONObject arrowsObject = jsonObj.optJSONObject("arrowsJSON");
            JSONArray trips = arrowsObject.optJSONArray("trips");
            JSONArray tripVehicleAssignments = arrowsObject.optJSONArray("tripVehicleAssignment");
            JSONArray passengers = arrowsObject.optJSONArray("passenger");
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
                cv.put(DBContract.Trip.COLUMN_TRIP_ID, trip.optInt("tripID"));
                cv.put(DBContract.Trip.COLUMN_TRIP_DATE, trip.optString("tripDate"));
                cv.put(DBContract.Trip.COLUMN_DEP_TIME, trip.optString("depTime"));
                cv.put(DBContract.Trip.COLUMN_ARRIVAL_TIME, trip.optString("arrivalTime"));
                cv.put(DBContract.Trip.COLUMN_DURATION, trip.optDouble("duration"));
                cv.put(DBContract.Trip.COLUMN_TRIP_SCHED_ID, trip.optInt("tripSchedID"));
                cv.put(DBContract.Trip.COLUMN_STATUS_CODE, trip.optString("statusCode"));
                db.insert(DBContract.Trip.TABLE_TRIP, null, cv);
            }

            //store all trips into database
            for(int i = 0; i < tripVehicleAssignments.length(); i++){
                JSONObject tripVehicleAssignment = tripVehicleAssignments.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.TripVehicleAssignment.COLUMN_ASSIGN_ID, tripVehicleAssignment.optInt("assignID"));
                cv.put(DBContract.TripVehicleAssignment.COLUMN_TRIP_ID, tripVehicleAssignment.optString("tripID"));
                cv.put(DBContract.TripVehicleAssignment.COLUMN_VEHICLE_ID, tripVehicleAssignment.optString("vehicleID"));
                cv.put(DBContract.TripVehicleAssignment.COLUMN_DRIVER_ID, tripVehicleAssignment.optString("driverID"));
                cv.put(DBContract.TripVehicleAssignment.COLUMN_STATUS_CODE, tripVehicleAssignment.optString("statusCode"));
                db.insert(DBContract.TripVehicleAssignment.TABLE_TRIP_VEHICLE_ASSIGNMENT, null, cv);
            }

            //store all passengers into database
            for(int i = 0; i < passengers.length(); i++){
                JSONObject passenger = passengers.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Passenger.COLUMN_PASSENGER_ID, passenger.optInt("passengerID"));
                cv.put(DBContract.Passenger.COLUMN_TAP_IN, passenger.optString("tapIn"));
                cv.put(DBContract.Passenger.COLUMN_TAP_OUT, passenger.optString("tapOut"));
                cv.put(DBContract.Passenger.COLUMN_DISEMBARKATION_PT, passenger.optString("disembarkationPt"));
                cv.put(DBContract.Passenger.COLUMN_DESTINATION, passenger.optString("destination"));
                cv.put(DBContract.Passenger.COLUMN_RESERVATION_NUM, passenger.optInt("reservationNum"));
                cv.put(DBContract.Passenger.COLUMN_VEHICLE_ID, passenger.optString("vehicleID"));
                cv.put(DBContract.Passenger.COLUMN_DRIVER_ID, passenger.optInt("driverID"));
                db.insert(DBContract.Passenger.TABLE_PASSENGER, null, cv);
            }

            //store all drivers into database
            for(int i = 0; i < drivers.length(); i++){
                JSONObject driver = drivers.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Driver.COLUMN_DRIVER_ID, driver.optInt("driverID"));
                cv.put(DBContract.Driver.COLUMN_LAST_NAME, driver.optString("lastName"));
                cv.put(DBContract.Driver.COLUMN_FIRST_NAME, driver.optString("firstName"));
                cv.put(DBContract.Driver.COLUMN_NICKNAME, driver.optString("nickname"));
                cv.put(DBContract.Driver.COLUMN_STATUS_CODE, driver.optString("statusCode"));
                db.insert(DBContract.Driver.TABLE_DRIVER, null, cv);
            }

            //store all vehicles into database
            for(int i = 0; i < vehicles.length(); i++){
                JSONObject vehicle = vehicles.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Vehicle.COLUMN_VEHICLE_ID, vehicle.optString("vehicleID"));
                cv.put(DBContract.Vehicle.COLUMN_VEHICLE_TYPE, vehicle.optString("vehicleType"));
                cv.put(DBContract.Vehicle.COLUMN_CAPACITY, vehicle.optInt("capacity"));
                cv.put(DBContract.Vehicle.COLUMN_IMAGE, vehicle.optString("image"));
                cv.put(DBContract.Vehicle.COLUMN_PLATE_NUM, vehicle.optString("plateNum"));
                cv.put(DBContract.Vehicle.COLUMN_MODEL, vehicle.optString("model"));
                cv.put(DBContract.Vehicle.COLUMN_BRAND, vehicle.optString("brand"));
                cv.put(DBContract.Vehicle.COLUMN_STATUS_CODE, vehicle.optString("statusCode"));
                db.insert(DBContract.Vehicle.TABLE_VEHICLE, null, cv);
            }

            //store all tripScheds into database
            for(int i = 0; i < tripScheds.length(); i++){
                JSONObject tripSched = tripScheds.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.TripSched.COLUMN_TRIP_SCHED_ID, tripSched.optInt("tripSchedID"));
                cv.put(DBContract.TripSched.COLUMN_TRIP_NUM, tripSched.optString("tripNum"));
                cv.put(DBContract.TripSched.COLUMN_DEP_TIME, tripSched.optString("depTime"));
                cv.put(DBContract.TripSched.COLUMN_ROUTE_ID, tripSched.optInt("routeID"));
                db.insert(DBContract.TripSched.TABLE_TRIP_SCHED, null, cv);
            }

            //store all routes into database
            for(int i = 0; i < routes.length(); i++){
                JSONObject route = routes.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Route.COLUMN_ROUTE_ID, route.optInt("routeID"));
                cv.put(DBContract.Route.COLUMN_ROUTE_ORIGIN, route.optString("origin"));
                cv.put(DBContract.Route.COLUMN_ROUTE_DESTINATION, route.optString("destination"));
                cv.put(DBContract.Route.COLUMN_ROUTE_DESCRIPTION, route.optString("routeDescription"));
                cv.put(DBContract.Route.COLUMN_LINE_NUM, route.optInt("lineNum"));
                db.insert(DBContract.Route.TABLE_ROUTE, null, cv);
            }

            //store all lines into database
            for(int i = 0; i < lines.length(); i++){
                JSONObject line = lines.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Line.COLUMN_LINE_NUM, line.optInt("lineNum"));
                cv.put(DBContract.Line.COLUMN_LINE_NAME, line.optString("lineName"));
                cv.put(DBContract.Line.COLUMN_STATUS_CODE, line.optString("statusCode"));
                db.insert(DBContract.Line.TABLE_LINE, null, cv);
            }

            //store all routeStops into database
            for(int i = 0; i < routeStops.length(); i++){
                JSONObject routeStop = routeStops.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.RouteStop.COLUMN_STOP_NUM, routeStop.optString("stopNum"));
                cv.put(DBContract.RouteStop.COLUMN_ORDER, routeStop.optInt("order"));
                cv.put(DBContract.RouteStop.COLUMN_ROUTE_ID, routeStop.optInt("routeID"));
                cv.put(DBContract.RouteStop.COLUMN_STOP_ID, routeStop.optInt("stopID"));
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
                cv.put(DBContract.User.COLUMN_STATUS_CODE, user.optString("statusCode"));
                db.insert(DBContract.User.TABLE_USER, null, cv);
            }

            //store all reservations into database
            for(int i = 0; i < reservations.length(); i++){
                JSONObject reservation = reservations.getJSONObject(i);
                cv = new ContentValues();
                cv.put(DBContract.Reservation.COLUMN_RESERVATION_NUM, reservation.optInt("reservationNum"));
                cv.put(DBContract.Reservation.COLUMN_TIMESTAMP, reservation.optInt("timestamp"));
                cv.put(DBContract.Reservation.COLUMN_DESTINATION, reservation.optInt("destination"));
                cv.put(DBContract.Reservation.COLUMN_TRIP_ID, reservation.optInt("tripID"));
                cv.put(DBContract.Reservation.COLUMN_STOP_NUM, reservation.optString("stopNum"));
                cv.put(DBContract.Reservation.COLUMN_ID_NUM, reservation.optInt("idNum"));
                cv.put(DBContract.Reservation.COLUMN_STATUS_CODE, reservation.optString("statusCode"));
                db.insert(DBContract.Reservation.TABLE_RESERVATION, null, cv);
            }

            db.close();
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    // updates db according to new json data only if the trip's departure time later than current time
    public void jsonUpdateToDb(Context context){
        try {
            DBHandler dbHandler = new DBHandler(context);
            SQLiteDatabase db = dbHandler.getWritableDatabase();
            ContentValues cv;
            // parse data into the database
            JSONObject jsonObj = this.json;
            JSONObject arrowsObject = jsonObj.optJSONObject("arrowsJSON");
            JSONArray trips = arrowsObject.optJSONArray("trips");
            JSONArray passengers = arrowsObject.optJSONArray("passengers");
            JSONArray tripScheds = arrowsObject.optJSONArray("tripScheds");
            JSONArray reservations = arrowsObject.optJSONArray("reservations");
            // sub json objects for multiple tables
            JSONObject tripVehicleAssignment;
            JSONObject status;
            int tripSchedID;
            String time;
            Date depTime = null, currentTime = null;
            SimpleDateFormat sentFormat = new SimpleDateFormat("h:mm:ss");
            Calendar cal;

            //update all trips into database
            for(int i = 0; i < trips.length(); i++){
                JSONObject trip = trips.getJSONObject(i);
                tripSchedID = trip.optInt("tripSchedID");
                for(int j = 0; j < tripScheds.length(); j++){
                    JSONObject tripSched = tripScheds.getJSONObject(j);
                    if(tripSchedID == tripSched.optInt("tripSchedID")){
                        try {
                            cal = Calendar.getInstance();
                            time = sentFormat.format(cal.getTime()); // reset for proper format and removal of other data
                            currentTime = sentFormat.parse(time);
                            time = tripSched.optString("depTime");
                            depTime = sentFormat.parse(time);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        // compare current time and trip departure time
                        if(depTime.after(currentTime)){

                            // update trips
                            cv = new ContentValues();
                            cv.put(DBContract.Trip.COLUMN_TRIP_ID, trip.optInt("tripID"));
                            cv.put(DBContract.Trip.COLUMN_TRIP_DATE, trip.optString("tripDate"));
                            cv.put(DBContract.Trip.COLUMN_DEP_TIME, trip.optString("depTime"));
                            cv.put(DBContract.Trip.COLUMN_ARRIVAL_TIME, trip.optString("arrivalTime"));
                            cv.put(DBContract.Trip.COLUMN_DURATION, trip.optDouble("duration"));
                            cv.put(DBContract.Trip.COLUMN_TRIP_SCHED_ID, trip.optInt("tripSchedID"));
                            cv.put(DBContract.Trip.COLUMN_STATUS_CODE, trip.optString("statusCode"));
                            db.update(DBContract.Trip.TABLE_TRIP, cv, DBContract.Trip.COLUMN_TRIP_ID + "=" + Integer.toString(trip.optInt("tripID")), null);

                            //first clear reservations attached to trip which deletes passengers through cascade
                            db.execSQL("DELETE FROM " + DBContract.Reservation.TABLE_RESERVATION + " WHERE " + DBContract.Reservation.COLUMN_TRIP_ID + " = " + trip.optInt("tripID"));
                            // update reservations related to trip
                            for(int k = 0; k < reservations.length(); k++){
                                JSONObject reservation = reservations.getJSONObject(k);
                                if(reservation.optInt("tripID") == trip.optInt("tripID")){
                                    cv = new ContentValues();
                                    cv.put(DBContract.Reservation.COLUMN_RESERVATION_NUM, reservation.optInt("reservationNum"));
                                    cv.put(DBContract.Reservation.COLUMN_TIMESTAMP, reservation.optInt("timestamp"));
                                    cv.put(DBContract.Reservation.COLUMN_DESTINATION, reservation.optInt("destination"));
                                    cv.put(DBContract.Reservation.COLUMN_TRIP_ID, reservation.optInt("tripID"));
                                    cv.put(DBContract.Reservation.COLUMN_STOP_NUM, reservation.optString("stopNum"));
                                    cv.put(DBContract.Reservation.COLUMN_ID_NUM, reservation.optInt("idNum"));
                                    cv.put(DBContract.Reservation.COLUMN_STATUS_CODE, reservation.optString("statusCode"));
                                    db.insert(DBContract.Reservation.TABLE_RESERVATION, null, cv);

                                    // insert reservation based passengers
                                    for(int n = 0; n < passengers.length(); n++){
                                        JSONObject passenger = passengers.getJSONObject(n);
                                        if(reservation.optInt("reservationNum") == passenger.optInt("reservationNum")){
                                            cv = new ContentValues();
                                            cv.put(DBContract.Passenger.COLUMN_PASSENGER_ID, passenger.optInt("passengerID"));
                                            cv.put(DBContract.Passenger.COLUMN_TAP_IN, passenger.optString("tapIn"));
                                            cv.put(DBContract.Passenger.COLUMN_TAP_OUT, passenger.optString("tapOut"));
                                            cv.put(DBContract.Passenger.COLUMN_DISEMBARKATION_PT, passenger.optString("disembarkationPt"));
                                            cv.put(DBContract.Passenger.COLUMN_DESTINATION, passenger.optString("destination"));
                                            cv.put(DBContract.Passenger.COLUMN_RESERVATION_NUM, passenger.optInt("reservationNum"));
                                            cv.put(DBContract.Passenger.COLUMN_VEHICLE_ID, passenger.optString("vehicleID"));
                                            cv.put(DBContract.Passenger.COLUMN_DRIVER_ID, passenger.optInt("driverID"));
                                            db.insert(DBContract.Passenger.TABLE_PASSENGER, null, cv);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            db.close();
        } catch (final JSONException e) {
            Log.e(TAG, "Json parsing error: " + e.getMessage());
        }
    }

    public JSONObject dbToJson(Context context) throws JSONException {
        DBHandler dbHandler = new DBHandler(context);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        JSONObject arrowsObject = new JSONObject();

        // get all trips and put contents into json array
        Cursor c = db.query(DBContract.Trip.TABLE_TRIP, null, null, null, null, null, null);
        JSONArray trips = new JSONArray();
        if(c.moveToFirst()){
            JSONObject trip = new JSONObject();
            do{
                trip.put("tripID", c.getInt(c.getColumnIndex(DBContract.Trip.COLUMN_TRIP_ID)));
                trip.put("tripDate", c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_TRIP_DATE)));
                trip.put("depTime", c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_DEP_TIME)));
                trip.put("arrivalTime", c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_ARRIVAL_TIME)));
                trip.put("duration", c.getDouble(c.getColumnIndex(DBContract.Trip.COLUMN_DURATION)));
                trip.put("tripSchedID", c.getInt(c.getColumnIndex(DBContract.Trip.COLUMN_TRIP_SCHED_ID)));
                trip.put("statusCode", c.getInt(c.getColumnIndex(DBContract.Trip.COLUMN_STATUS_CODE)));
                trips.put(trip);
            }while(c.moveToNext());
        }

        // get all reservations and put contents into json array
        c = db.query(DBContract.Reservation.TABLE_RESERVATION, null, null, null, null, null, null);
        JSONArray reservations = new JSONArray();
        if(c.moveToFirst()){
            JSONObject reservation = new JSONObject();
            do{
                reservation.put("reservationNum", c.getInt(c.getColumnIndex(DBContract.Reservation.COLUMN_RESERVATION_NUM)));
                reservation.put("timestamp", c.getString(c.getColumnIndex(DBContract.Reservation.COLUMN_TIMESTAMP)));
                reservation.put("destination", c.getString(c.getColumnIndex(DBContract.Reservation.COLUMN_DESTINATION)));
                reservation.put("idNum", c.getInt(c.getColumnIndex(DBContract.Reservation.COLUMN_ID_NUM)));
                reservation.put("tripID", c.getInt(c.getColumnIndex(DBContract.Reservation.COLUMN_TRIP_ID)));
                reservation.put("stopNum", c.getString(c.getColumnIndex(DBContract.Reservation.COLUMN_STOP_NUM)));
                reservation.put("statusCode", c.getInt(c.getColumnIndex(DBContract.Reservation.COLUMN_STATUS_CODE)));
                reservations.put(reservation);
            }while(c.moveToNext());
        }

        // get all passengers and put contents into json array
        c = db.query(DBContract.Passenger.TABLE_PASSENGER, null, null, null, null, null, null);
        JSONArray passengers = new JSONArray();
        if(c.moveToFirst()){
            JSONObject passenger = new JSONObject();
            do{
                Log.e(TAG, "passenger");
                passenger.put("passengerID", c.getInt(c.getColumnIndex(DBContract.Passenger.COLUMN_PASSENGER_ID)));
                passenger.put("tapIn", c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_TAP_IN)));
                passenger.put("tapOut", c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_TAP_OUT)));
                passenger.put("disembarkationPt", c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_DISEMBARKATION_PT)));
                passenger.put("destination", c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_DESTINATION)));
                passenger.put("reservationNum", c.getInt(c.getColumnIndex(DBContract.Passenger.COLUMN_RESERVATION_NUM)));
                passenger.put("vehicleID", c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_VEHICLE_ID)));
                passenger.put("driverID", c.getInt(c.getColumnIndex(DBContract.Passenger.COLUMN_DRIVER_ID)));
                passengers.put(passenger);
            }while(c.moveToNext());
        }
        c.close();
        arrowsObject.put("trips", trips);
        arrowsObject.put("reservations", reservations);
        arrowsObject.put("passengers", passengers);
        db.close();
        return arrowsObject;
    }

}
