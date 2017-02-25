package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.ContentValues.TAG;
import static com.example.aaron.arrowsmobile.DBContract.Passenger.COLUMN_PASSENGER_ID;
import static com.example.aaron.arrowsmobile.DBContract.Passenger.COLUMN_PASSENGER_USER;

public class DBHandler extends SQLiteOpenHelper{

    final static String SCHEMA = "arrowsDB.db";
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");

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
        cv.putNull(DBContract.Trip.COLUMN_ARRIVAL_TIME);
        cv.put(DBContract.Trip.COLUMN_DURATION, 60.00);
        cv.put(DBContract.Trip.COLUMN_IS_SPECIAL, false);
        cv.put(DBContract.Trip.COLUMN_SP_NUM_PASS, -1);
        cv.put(DBContract.Trip.COLUMN_PURPOSE, "Sample Trip");
        cv.put(DBContract.Trip.COLUMN_TRIP_VEHICLE, "1234567890");
        cv.put(DBContract.Trip.COLUMN_TRIP_DRIVER, 1234567);
        cv.put(DBContract.Trip.COLUMN_TRIP_TRIP_SCHED, 0000000001);
        insertTest = db.insert(DBContract.Trip.TABLE_TRIP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Passenger1
        cv = new ContentValues();
        cv.put(COLUMN_PASSENGER_ID, 0000000001);
        cv.put(DBContract.Passenger.COLUMN_FEEDBACK_ON, "none");
        cv.put(DBContract.Passenger.COLUMN_FEEDBACK, 0);
        cv.putNull(DBContract.Passenger.COLUMN_TAP_IN);
        cv.putNull(DBContract.Passenger.COLUMN_TAP_OUT);
        cv.put(DBContract.Passenger.COLUMN_DISEMBARKATION_PT, "Manila Campus");
        cv.put(DBContract.Passenger.COLUMN_DESTINATION, "STC CAMPUS");
        cv.put(DBContract.Passenger.COLUMN_IS_CHANCE, false);
        cv.put(DBContract.Passenger.COLUMN_PASSENGER_TRIP, 0000000001);
        cv.put(COLUMN_PASSENGER_USER, 11407883);
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
        cv.put(DBContract.Vehicle.COLUMN_CAPACITY, 3);
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
        cv.put(DBContract.TripSched.COLUMN_TRIP_SCHED_ROUTE, 00001);
        insertTest = db.insert(DBContract.TripSched.TABLE_TRIP_SCHED, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Route1
        cv = new ContentValues();
        cv.put(DBContract.Route.COLUMN_ROUTE_ID, 00001);
        cv.put(DBContract.Route.COLUMN_ROUTE_NAME, "Manila to STC");
        cv.put(DBContract.Route.COLUMN_ROUTE_DESCRIPTION, "Route for Manila to STC");
        cv.put(DBContract.Route.COLUMN_ROUTE_LINE, 00001);
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
        cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_STOP, 00000);
        cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_ROUTE, 00001);
        insertTest = db.insert(DBContract.RouteStop.TABLE_ROUTE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Stop1
        cv = new ContentValues();
        cv.put(DBContract.Stop.COLUMN_STOP_ID, 00000);
        cv.put(DBContract.Stop.COLUMN_STOP_NAME, "First Stop");
        cv.put(DBContract.Stop.COLUMN_LATITUDE, "20.00");
        cv.put(DBContract.Stop.COLUMN_LONGITUDE, "20.00");
        insertTest = db.insert(DBContract.Stop.TABLE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // RouteStop2
        cv = new ContentValues();
        cv.put(DBContract.RouteStop.COLUMN_STOP_NUM, "00001");
        cv.put(DBContract.RouteStop.COLUMN_ORDER, 00001);
        cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_STOP, 00001);
        cv.put(DBContract.RouteStop.COLUMN_ROUTE_STOP_ROUTE, 00001);
        insertTest = db.insert(DBContract.RouteStop.TABLE_ROUTE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // Stop2
        cv = new ContentValues();
        cv.put(DBContract.Stop.COLUMN_STOP_ID, 00001);
        cv.put(DBContract.Stop.COLUMN_STOP_NAME, "Second Stop");
        cv.put(DBContract.Stop.COLUMN_LATITUDE, "60.00");
        cv.put(DBContract.Stop.COLUMN_LONGITUDE, "60.00");
        insertTest = db.insert(DBContract.Stop.TABLE_STOP, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        // User1
        cv = new ContentValues();
        cv.put(DBContract.User.COLUMN_ID_NUM, 11407883);
        cv.put(DBContract.User.COLUMN_NAME, "Aaron M. Candelaria");
        cv.put(DBContract.User.COLUMN_EMAIL, "email@example.com");
        cv.put(DBContract.User.COLUMN_EMERGENCY_CONTACT, "Emergency Contact");
        cv.put(DBContract.User.COLUMN_EMERGENCY_CONTACT_NUM, "12345678911");
        cv.put(DBContract.User.COLUMN_IS_ADMIN, false);
        cv.putNull(DBContract.User.COLUMN_ADMIN_PASSWORD);
        insertTest = db.insert(DBContract.User.TABLE_USER, null, cv);
        if(dbInsertTest(insertTest)){
            return false;
        }

        return true;
    }

    public ArrayList<KeyHandler> getTripKeyHolderList(){
        ArrayList<KeyHandler> keyHandlerList = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cTrip = db.query(DBContract.Trip.TABLE_TRIP, null, null, null, null, null, null);
        if(cTrip.moveToFirst()) {
            KeyHandler keyHandler = null;
            int tripID = 0, driverID = 0, tripSchedID = 0, routeID = 0, lineID = 0;
            String vehicleID = null, order = null;
            String[] columns = {"", null}, selection = {""};
            ArrayList<Integer> stopIDList = new ArrayList<>(), passengerIDList = new ArrayList<>(), userIDList = new ArrayList<>();
            do {
                tripID = cTrip.getInt(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_ID));
                vehicleID = cTrip.getString(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_VEHICLE));
                driverID = cTrip.getInt(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_DRIVER));
                tripSchedID = cTrip.getInt(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_TRIP_SCHED));

                // get routeID from tripSchedID route foreign key
                columns[0] = DBContract.TripSched.COLUMN_TRIP_SCHED_ROUTE;
                selection[0] = String.valueOf(tripSchedID);
                Cursor cRoute = db.query(DBContract.TripSched.TABLE_TRIP_SCHED,
                        columns,
                        " " + DBContract.TripSched.COLUMN_TRIP_SCHED_ID + " = ? ",
                        selection,
                        null,
                        null,
                        null);
                if(cRoute.moveToFirst()){
                    routeID = cRoute.getInt(cRoute.getColumnIndex(DBContract.TripSched.COLUMN_TRIP_SCHED_ROUTE));
                }
                else{
                    Log.e(TAG, "routeID foreign key NOT FOUND");
                }
                cRoute.close();

                // get lineID from routeID line foreign key
                columns[0] = DBContract.Route.COLUMN_ROUTE_LINE;
                selection[0] = String.valueOf(routeID);
                Cursor cLine = db.query(DBContract.Route.TABLE_ROUTE,
                        columns,
                        " " + DBContract.Route.COLUMN_ROUTE_ID + " = ? ",
                        selection,
                        null,
                        null,
                        null);
                if(cLine.moveToFirst()){
                    lineID = cLine.getInt(cLine.getColumnIndex(DBContract.Route.COLUMN_ROUTE_LINE));
                }
                else{
                    Log.e(TAG, "routeID foreign key NOT FOUND");
                }
                cLine.close();

                // get list of stopID using routestops junction table routeID relationship
                columns[0] = DBContract.RouteStop.COLUMN_ROUTE_STOP_STOP;
                selection[0] = String.valueOf(routeID);
                Cursor cStop = db.query(DBContract.RouteStop.TABLE_ROUTE_STOP,
                        columns,
                        " " + DBContract.RouteStop.COLUMN_ROUTE_STOP_ROUTE + " = ? ",
                        selection,
                        null,
                        null,
                        null);
                if(cStop.moveToFirst()){
                    do{
                        stopIDList.add(cStop.getInt(cStop.getColumnIndex((DBContract.RouteStop.COLUMN_ROUTE_STOP_STOP))));
                    } while( cStop.moveToNext());
                }
                else{
                    Log.e(TAG, "stopID foreign keys NOT FOUND");
                }
                cStop.close();

                // get list of passengerID using passenger tripID foreign key while also populating the list of userID using userID foreign key
                columns[0] = DBContract.Passenger.COLUMN_PASSENGER_ID;
                columns[1] = DBContract.Passenger.COLUMN_PASSENGER_USER;
                selection[0] = String.valueOf(tripID);
                Cursor cPassenger = db.query(DBContract.Passenger.TABLE_PASSENGER,
                        columns,
                        " " + DBContract.Passenger.COLUMN_PASSENGER_TRIP + " = ? ",
                        selection,
                        null,
                        null,
                        null);
                if(cPassenger.moveToFirst()){
                    do{
                        passengerIDList.add(cPassenger.getInt(cPassenger.getColumnIndex((DBContract.Passenger.COLUMN_PASSENGER_ID))));
                        userIDList.add(cPassenger.getInt(cPassenger.getColumnIndex((DBContract.Passenger.COLUMN_PASSENGER_USER))));
                    } while( cPassenger.moveToNext());
                }
                else{
                    Log.e(TAG, "tripID passenger foreign keys NOT FOUND");
                }
                cPassenger.close();

                keyHandler = new KeyHandler(tripID, vehicleID, passengerIDList, driverID, tripSchedID, routeID, lineID, stopIDList, userIDList);
                keyHandlerList.add(keyHandler);
            } while (cTrip.moveToNext());
            cTrip.close();
        }
        db.close();
        return keyHandlerList;
    }

}
