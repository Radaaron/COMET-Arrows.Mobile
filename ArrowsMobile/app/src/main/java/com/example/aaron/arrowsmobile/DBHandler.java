package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.ContentValues.TAG;

public class DBHandler extends SQLiteOpenHelper{

    final static String SCHEMA = "arrowsDB.db";

    public DBHandler(Context context) {
        super(context, SCHEMA, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create all tables
        db.execSQL(DBContract.CREATE_TABLE_ACADEMIC_PERIOD);
        db.execSQL(DBContract.CREATE_TABLE_CAN_DRIVE);
        // db.execSQL(DBContract.CREATE_TABLE_CLASS_ASSIGNMENT);
        db.execSQL(DBContract.CREATE_TABLE_CLASS_SCHEDULE);
        db.execSQL(DBContract.CREATE_TABLE_DRIVER);
        db.execSQL(DBContract.CREATE_TABLE_LINE);
        db.execSQL(DBContract.CREATE_TABLE_PASSENGER);
        db.execSQL(DBContract.CREATE_TABLE_RESERVATION);
        db.execSQL(DBContract.CREATE_TABLE_ROUTE);
        db.execSQL(DBContract.CREATE_TABLE_ROUTE_STOP);
        db.execSQL(DBContract.CREATE_TABLE_STATUS);
        db.execSQL(DBContract.CREATE_TABLE_STOP);
        db.execSQL(DBContract.CREATE_TABLE_SYSTEM_CONFIG);
        db.execSQL(DBContract.CREATE_TABLE_TRIP);
        db.execSQL(DBContract.CREATE_TABLE_TRIP_SCHED);
        db.execSQL(DBContract.CREATE_TABLE_USER);
        db.execSQL(DBContract.CREATE_TABLE_USER_TYPE);
        db.execSQL(DBContract.CREATE_TABLE_VEHICLE);
        // create mock values first time
        if(!createMockData(db)){
            System.out.println("INSERT ERROR");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // on upgrade drop older tables
        db.execSQL(DBContract.DELETE_ACADEMIC_PERIOD);
        db.execSQL(DBContract.DELETE_CAN_DRIVE);
        // db.execSQL(DBContract.DELETE_CLASS_ASSIGNMENT);
        db.execSQL(DBContract.DELETE_CLASS_SCHEDULE);
        db.execSQL(DBContract.DELETE_DRIVER);
        db.execSQL(DBContract.DELETE_LINE);
        db.execSQL(DBContract.DELETE_PASSENGER);
        db.execSQL(DBContract.DELETE_RESERVATION);
        db.execSQL(DBContract.DELETE_ROUTE);
        db.execSQL(DBContract.DELETE_ROUTE_STOP);
        db.execSQL(DBContract.DELETE_STATUS);
        db.execSQL(DBContract.DELETE_STOP);
        db.execSQL(DBContract.DELETE_SYSTEM_CONFIG);
        db.execSQL(DBContract.DELETE_TRIP);
        db.execSQL(DBContract.DELETE_TRIP_SCHED);
        db.execSQL(DBContract.DELETE_USER);
        db.execSQL(DBContract.DELETE_USER_TYPE);
        db.execSQL(DBContract.DELETE_VEHICLE);
        // create new tables
        onCreate(db);
    }

    // checks if data insert is not successful
    public boolean dbInsertTest(long test){
        if(test == -1){
            return true;
        }
        return false;
    }

    // storing mock values in db (for testing only)
    public boolean createMockData(SQLiteDatabase db){
        ContentValues cv = new ContentValues();
        long insertTest;
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat date = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm");

        // Trip1
        cv.put(DBContract.Trip.COLUMN_TRIP_ID, 0000000001);
        cv.put(DBContract.Trip.COLUMN_REMARKS, "Sample trip");
        cv.put(DBContract.Trip.COLUMN_TRIP_DATE, date.format(calendar.getTime()));
        calendar.set(Calendar.HOUR, 06);
        calendar.set(Calendar.MINUTE, 00);
        cv.put(DBContract.Trip.COLUMN_DEP_TIME, time.format(calendar.getTime()));
        calendar.set(Calendar.HOUR, 07);
        calendar.set(Calendar.MINUTE, 00);
        cv.put(DBContract.Trip.COLUMN_ARRIVAL_TIME, time.format(calendar.getTime()));
        cv.put(DBContract.Trip.COLUMN_DURATION, 60.00);
        cv.put(DBContract.Trip.COLUMN_IS_SPECIAL, false);
        cv.put(DBContract.Trip.COLUMN_SP_NUM_PASS, -1);
        cv.put(DBContract.Trip.COLUMN_PURPOSE, "Sample Trip");
        insertTest = db.insert(DBContract.Trip.TABLE_TRIP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Passenger1
        cv = new ContentValues();
        cv.put(DBContract.Passenger.COLUMN_PASSENGER_ID, 11407883);
        cv.put(DBContract.Passenger.COLUMN_FEEDBACK_ON, "none");
        cv.put(DBContract.Passenger.COLUMN_FEEDBACK, 0);
        cv.putNull(DBContract.Passenger.COLUMN_TAP_IN);
        cv.putNull(DBContract.Passenger.COLUMN_TAP_OUT);
        cv.put(DBContract.Passenger.COLUMN_DISEMBARKATION_PT, "Manila Campus");
        cv.put(DBContract.Passenger.COLUMN_DESTINATION, "STC CAMPUS");
        cv.put(DBContract.Passenger.COLUMN_IS_CHANCE, false);
        insertTest = db.insert(DBContract.Passenger.TABLE_PASSENGER, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Driver1
        cv = new ContentValues();
        cv.put(DBContract.Driver.COLUMN_DRIVER_ID, 1234567);
        cv.put(DBContract.Driver.COLUMN_LAST_NAME, "Smith");
        cv.put(DBContract.Driver.COLUMN_FIRST_NAME, "John");
        cv.put(DBContract.Driver.COLUMN_NICKNAME, "The Doctor");
        insertTest = db.insert(DBContract.Driver.TABLE_DRIVER, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Vehicle1
        cv = new ContentValues();
        cv.put(DBContract.Vehicle.COLUMN_VEHICLE_ID, "1234567890");
        cv.put(DBContract.Vehicle.COLUMN_VEHICLE_TYPE, "Large");
        cv.put(DBContract.Vehicle.COLUMN_CAPACITY, 12);
        cv.put(DBContract.Vehicle.COLUMN_IMAGE, "tempImage");
        cv.put(DBContract.Vehicle.COLUMN_PLATE_NUM, "1234567");
        cv.put(DBContract.Vehicle.COLUMN_MODEL, "tempModel");
        cv.put(DBContract.Vehicle.COLUMN_BRAND, "tempBrand");
        insertTest = db.insert(DBContract.Vehicle.TABLE_VEHICLE, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // TripSched1
        cv = new ContentValues();
        cv.put(DBContract.TripSched.COLUMN_TRIP_SCHED_ID, 0000000001);
        cv.put(DBContract.TripSched.COLUMN_TRIP_NUM, "1234567890");
        calendar.set(Calendar.HOUR, 06);
        calendar.set(Calendar.MINUTE, 00);
        cv.put(DBContract.TripSched.COLUMN_DEP_TIME, time.format(calendar.getTime()));
        insertTest = db.insert(DBContract.TripSched.TABLE_TRIP_SCHED, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Route1
        cv = new ContentValues();
        cv.put(DBContract.Route.COLUMN_ROUTE_ID, 00001);
        cv.put(DBContract.Route.COLUMN_ROUTE_NAME, "Manila to STC");
        cv.put(DBContract.Route.COLUMN_ROUTE_DESCRIPTION, "Route for Manila to STC");
        insertTest = db.insert(DBContract.Route.TABLE_ROUTE, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Line1
        cv = new ContentValues();
        cv.put(DBContract.Line.COLUMN_LINE_NUM, 00001);
        cv.put(DBContract.Line.COLUMN_LINE_NAME, "Line 1");
        insertTest = db.insert(DBContract.Line.TABLE_LINE, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // RouteStop1
        cv = new ContentValues();
        cv.put(DBContract.RouteStop.COLUMN_STOP_NUM, "00000");
        cv.put(DBContract.RouteStop.COLUMN_ORDER, 00000);
        insertTest = db.insert(DBContract.RouteStop.TABLE_ROUTE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Stop1
        cv = new ContentValues();
        cv.put(DBContract.Stop.COLUMN_STOP_ID, 00000);
        cv.put(DBContract.Stop.COLUMN_STOP_NAME, "First Stop");
        cv.put(DBContract.Stop.COLUMN_LATITUDE, "100.00");
        cv.put(DBContract.Stop.COLUMN_LONGITUDE, "100.00");
        insertTest = db.insert(DBContract.Stop.TABLE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // RouteStop2
        cv = new ContentValues();
        cv.put(DBContract.RouteStop.COLUMN_STOP_NUM, "00001");
        cv.put(DBContract.RouteStop.COLUMN_ORDER, 00001);
        insertTest = db.insert(DBContract.RouteStop.TABLE_ROUTE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Stop2
        cv = new ContentValues();
        cv.put(DBContract.Stop.COLUMN_STOP_ID, 00001);
        cv.put(DBContract.Stop.COLUMN_STOP_NAME, "Second Stop");
        cv.put(DBContract.Stop.COLUMN_LATITUDE, "150.00");
        cv.put(DBContract.Stop.COLUMN_LONGITUDE, "150.00");
        insertTest = db.insert(DBContract.Stop.TABLE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        return true;
    }

    // getting mock values (testing only)
    public ArrayList<Trip> getAllTrips(){
        SQLiteDatabase db = getReadableDatabase();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

        // inside out format: Stop -> RouteStop|Line -> Route -> TripSched|Vehicle|Driver|Passengers -> Trips
        Cursor c = db.query(DBContract.Stop.TABLE_STOP, null, null, null, null, null, null);
        ArrayList<Stop> stopList = new ArrayList();
        if(c.moveToFirst()){
            do{
                Stop stop = new Stop(c.getInt(c.getColumnIndex(DBContract.Stop.COLUMN_STOP_ID)), c.getString(c.getColumnIndex(DBContract.Stop.COLUMN_STOP_NAME)), c.getString(c.getColumnIndex(DBContract.Stop.COLUMN_LATITUDE)), c.getString(c.getColumnIndex(DBContract.Stop.COLUMN_LONGITUDE)));
                stopList.add(stop);
            } while( c.moveToNext());
        }

        c = db.query(DBContract.RouteStop.TABLE_ROUTE_STOP, null, null, null, null, null, null);
        ArrayList<RouteStop> routeStopList = new ArrayList();
        int i = 0; // one to one
        if(c.moveToFirst()){
            do{
                RouteStop routeStop = new RouteStop(c.getString(c.getColumnIndex(DBContract.RouteStop.COLUMN_STOP_NUM)), c.getInt(c.getColumnIndex(DBContract.RouteStop.COLUMN_ORDER)), stopList.get(i));
                routeStopList.add(routeStop);
                i++;
            } while( c.moveToNext());
        }

        c = db.query(DBContract.Line.TABLE_LINE, null, null, null, null, null, null);
        ArrayList<Line> lineList = new ArrayList();
        if(c.moveToFirst()){
            do{
                Line line = new Line(c.getInt(c.getColumnIndex(DBContract.Line.COLUMN_LINE_NUM)), c.getString(c.getColumnIndex(DBContract.Line.COLUMN_LINE_NAME)));
                lineList.add(line);
            } while( c.moveToNext());
        }

        c = db.query(DBContract.Route.TABLE_ROUTE, null, null, null, null, null, null);
        ArrayList<Route> routeList = new ArrayList();
        i = 0; // one to one
        if(c.moveToFirst()){
            do{
                Route route = new Route(c.getInt(c.getColumnIndex(DBContract.Route.COLUMN_ROUTE_ID)), c.getString(c.getColumnIndex(DBContract.Route.COLUMN_ROUTE_NAME)), c.getString(c.getColumnIndex(DBContract.Route.COLUMN_ROUTE_DESCRIPTION)), lineList.get(i), routeStopList);
                routeList.add(route);
                i++;
            } while( c.moveToNext());
        }

        c = db.query(DBContract.TripSched.TABLE_TRIP_SCHED, null, null, null, null, null, null);
        ArrayList<TripSched> tripSchedList = new ArrayList();
        i = 0; // one to one
        if(c.moveToFirst()){
            Date date = null;
            Calendar tripSchedDepTime = Calendar.getInstance();
            do{
                String depTime = c.getString(c.getColumnIndex(DBContract.TripSched.COLUMN_DEP_TIME));
                try {
                    date = timeFormat.parse(depTime);
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing ISO8601 datetime failed", e);
                }
                tripSchedDepTime.setTime(date);
                TripSched tripSched = new TripSched(c.getInt(c.getColumnIndex(DBContract.TripSched.COLUMN_TRIP_SCHED_ID)), c.getString(c.getColumnIndex(DBContract.TripSched.COLUMN_TRIP_NUM)), tripSchedDepTime, routeList.get(i));
                tripSchedList.add(tripSched);
                i++;
            } while( c.moveToNext());
        }

        c = db.query(DBContract.Vehicle.TABLE_VEHICLE, null, null, null, null, null, null);
        ArrayList<Vehicle> vehicleList = new ArrayList();
        if(c.moveToFirst()){
            do{
                Vehicle vehicle = new Vehicle(c.getString(c.getColumnIndex(DBContract.Vehicle.COLUMN_VEHICLE_ID)), c.getString(c.getColumnIndex(DBContract.Vehicle.COLUMN_VEHICLE_TYPE)), c.getInt(c.getColumnIndex(DBContract.Vehicle.COLUMN_CAPACITY)), c.getString(c.getColumnIndex(DBContract.Vehicle.COLUMN_IMAGE)), c.getString(c.getColumnIndex(DBContract.Vehicle.COLUMN_PLATE_NUM)), c.getString(c.getColumnIndex(DBContract.Vehicle.COLUMN_MODEL)), c.getString(c.getColumnIndex(DBContract.Vehicle.COLUMN_BRAND)));
                vehicleList.add(vehicle);
            } while( c.moveToNext());
        }

        c = db.query(DBContract.Driver.TABLE_DRIVER, null, null, null, null, null, null);
        ArrayList<Driver> driverList = new ArrayList();
        if(c.moveToFirst()){
            do{
                Driver driver = new Driver(c.getInt(c.getColumnIndex(DBContract.Driver.COLUMN_DRIVER_ID)), c.getString(c.getColumnIndex(DBContract.Driver.COLUMN_LAST_NAME)), c.getString(c.getColumnIndex(DBContract.Driver.COLUMN_FIRST_NAME)), c.getString(c.getColumnIndex(DBContract.Driver.COLUMN_NICKNAME)));
                driverList.add(driver);
            } while( c.moveToNext());
        }

        c = db.query(DBContract.Passenger.TABLE_PASSENGER, null, null, null, null, null, null);
        ArrayList<Passenger> passengerList = new ArrayList();
        if(c.moveToFirst()){
            Calendar tapIn  = Calendar.getInstance(), tapOut  = Calendar.getInstance();
            boolean isChance;
            do{
                isChance = c.getInt(c.getColumnIndex(DBContract.Passenger.COLUMN_IS_CHANCE)) > 0;
                Passenger passenger = new Passenger(c.getInt(c.getColumnIndex(DBContract.Passenger.COLUMN_PASSENGER_ID)), c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_FEEDBACK_ON)), c.getInt(c.getColumnIndex(DBContract.Passenger.COLUMN_FEEDBACK)), tapIn, tapOut, c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_DISEMBARKATION_PT)), c.getString(c.getColumnIndex(DBContract.Passenger.COLUMN_DESTINATION)), isChance);
                passengerList.add(passenger);
            } while( c.moveToNext());
        }

        c = db.query(DBContract.Trip.TABLE_TRIP, null, null, null, null, null, null);
        ArrayList<Trip> tripList = new ArrayList<>();
        i = 0; // one to one
        if(c.moveToFirst()){
            Date date = null;
            Calendar tripDate = Calendar.getInstance(), depTime  = Calendar.getInstance(), arrivalTime  = Calendar.getInstance();;
            boolean isSpecial;
            String tripDateTime;
            do{
                tripDateTime = c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_TRIP_DATE));
                try {
                    date = dateFormat.parse(tripDateTime);
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing ISO8601 datetime failed", e);;
                }
                tripDate.setTime(date);
                tripDateTime = c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_DEP_TIME));
                try {
                    date = timeFormat.parse(tripDateTime);
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing ISO8601 datetime failed", e);;
                }
                depTime.setTime(date);
                tripDateTime = c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_ARRIVAL_TIME));
                try {
                    date = timeFormat.parse(tripDateTime);
                } catch (ParseException e) {
                    Log.e(TAG, "Parsing ISO8601 datetime failed", e);;
                }
                arrivalTime.setTime(date);
                isSpecial = c.getInt(c.getColumnIndex(DBContract.Trip.COLUMN_IS_SPECIAL)) > 0;
                Trip trip = new Trip(c.getInt(c.getColumnIndex(DBContract.Trip.COLUMN_TRIP_ID)), c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_REMARKS)), tripDate, depTime, arrivalTime, c.getDouble(c.getColumnIndex(DBContract.Trip.COLUMN_DURATION)), isSpecial, c.getInt(c.getColumnIndex(DBContract.Trip.COLUMN_SP_NUM_PASS)), c.getString(c.getColumnIndex(DBContract.Trip.COLUMN_PURPOSE)), tripSchedList.get(i), vehicleList.get(i), driverList.get(i), passengerList);
                i++;
                tripList.add(trip);
            } while( c.moveToNext());
        }
        db.close();
        c.close();
        return tripList;
    }

}
