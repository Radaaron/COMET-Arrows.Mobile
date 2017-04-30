package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

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
        db.execSQL(DBContract.CREATE_TABLE_DRIVER);
        db.execSQL(DBContract.CREATE_TABLE_LINE);
        db.execSQL(DBContract.CREATE_TABLE_PASSENGER);
        db.execSQL(DBContract.CREATE_TABLE_RESERVATION);
        db.execSQL(DBContract.CREATE_TABLE_ROUTE);
        db.execSQL(DBContract.CREATE_TABLE_ROUTE_STOP);
        db.execSQL(DBContract.CREATE_TABLE_STOP);
        db.execSQL(DBContract.CREATE_TABLE_TRIP);
        db.execSQL(DBContract.CREATE_TABLE_TRIP_SCHED);
        db.execSQL(DBContract.CREATE_TABLE_USER);
        db.execSQL(DBContract.CREATE_TABLE_VEHICLE);
        db.execSQL(DBContract.CREATE_TABLE_LANDING);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // on upgrade drop older tables
        db.execSQL(DBContract.DELETE_DRIVER);
        db.execSQL(DBContract.DELETE_LINE);
        db.execSQL(DBContract.DELETE_PASSENGER);
        db.execSQL(DBContract.DELETE_RESERVATION);
        db.execSQL(DBContract.DELETE_ROUTE);
        db.execSQL(DBContract.DELETE_ROUTE_STOP);
        db.execSQL(DBContract.DELETE_STOP);
        db.execSQL(DBContract.DELETE_TRIP);
        db.execSQL(DBContract.DELETE_TRIP_SCHED);
        db.execSQL(DBContract.DELETE_USER);
        db.execSQL(DBContract.DELETE_VEHICLE);
        db.execSQL(DBContract.DELETE_LANDING);
        // create new tables
        onCreate(db);
    }

    public void createAppTables(){
        // Landing Table
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Local.COLUMN_LOCAL_ID, 1); // there should only be one
        cv.putNull(DBContract.Local.COLUMN_LOCAL_PLATE_NUM);
        cv.putNull(DBContract.Local.COLUMN_LOCAL_DRIVER);
        cv.putNull(DBContract.Local.COLUMN_LOCAL_ACTIVITY);
        db.insert(DBContract.Local.TABLE_LOCAL, null, cv);
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
            ArrayList<Integer> stopIDList, passengerIDList, userIDList, reservationNumList;
            do {
                stopIDList = new ArrayList<>();
                passengerIDList = new ArrayList<>();
                userIDList = new ArrayList<>();
                reservationNumList = new ArrayList<>();
                tripID = cTrip.getInt(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_ID));
                vehicleID = cTrip.getString(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_VEHICLE));
                driverID = cTrip.getInt(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_DRIVER));
                tripSchedID = cTrip.getInt(cTrip.getColumnIndex(DBContract.Trip.COLUMN_TRIP_TRIP_SCHED));

                // get routeID from tripSchedID route foreign key
                columns[1] = null; // reset
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

                // get list of reservation using reservation tripID foreign key while also populating the list of userID using userID foreign key
                columns[0] = DBContract.Reservation.COLUMN_RESERVATION_NUM;
                columns[1] = DBContract.Reservation.COLUMN_RESERVATION_USER;
                selection[0] = String.valueOf(tripID);
                Cursor cReservations = db.query(DBContract.Reservation.TABLE_RESERVATION,
                        columns,
                        " " + DBContract.Reservation.COLUMN_RESERVATION_TRIP + " = ? ",
                        selection,
                        null,
                        null,
                        null);
                if(cReservations.moveToFirst()){
                    do{
                        reservationNumList.add(cReservations.getInt(cReservations.getColumnIndex((DBContract.Reservation.COLUMN_RESERVATION_NUM))));
                        userIDList.add(cReservations.getInt(cReservations.getColumnIndex((DBContract.Reservation.COLUMN_RESERVATION_USER))));
                    } while( cReservations.moveToNext());
                }
                else{
                    Log.e(TAG, "tripID reservation foreign keys NOT FOUND");
                }
                cReservations.close();

                Cursor cPassenger = null;
                // get list of passengerID using reservationNum foreign keys
                columns[0] = DBContract.Passenger.COLUMN_PASSENGER_ID;
                columns[1] = null; // reset
                for(int i = 0; i < reservationNumList.size(); i++){
                    selection[0] = String.valueOf(reservationNumList.get(i));
                    cPassenger = db.query(DBContract.Passenger.TABLE_PASSENGER,
                            columns,
                            " " + DBContract.Passenger.COLUMN_PASSENGER_RESERVATION + " = ? ",
                            selection,
                            null,
                            null,
                            null);
                    if(cPassenger.moveToFirst()){
                        do{
                            passengerIDList.add(cPassenger.getInt(cPassenger.getColumnIndex((DBContract.Passenger.COLUMN_PASSENGER_ID))));
                        } while( cPassenger.moveToNext());
                    }
                    else{
                        Log.e(TAG, "passenger reservationNum foreign keys NOT FOUND");
                    }
                }
                if(cPassenger != null){
                    cPassenger.close();
                }

                keyHandler = new KeyHandler(tripID, vehicleID, passengerIDList, driverID, tripSchedID, routeID, lineID, stopIDList, userIDList, reservationNumList);
                keyHandlerList.add(keyHandler);
            } while (cTrip.moveToNext());
            cTrip.close();
        }
        db.close();
        return keyHandlerList;
    }

}
