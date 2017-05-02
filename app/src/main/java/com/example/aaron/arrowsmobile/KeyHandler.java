package com.example.aaron.arrowsmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

// holds the necessary primary keys for one trip and handles the retrieval of data using primary keys
public class KeyHandler implements Parcelable{

    private int tripID;
    private String vehicleID;
    private ArrayList<Integer> passengerIDList;
    private ArrayList<Integer> userIDList;
    private ArrayList<Integer> reservationNumList;
    private int driverID;
    private int tripSchedID;
    private int routeID;
    private int lineID;
    private ArrayList<Integer> stopIDList;

    private DBHandler dbHandler;

    public KeyHandler() {
        // empty constructor for method use only
    }

    public KeyHandler(int tripID, String vehicleID, ArrayList<Integer> passengerIDList, int driverID, int tripSchedID, int routeID, int lineID, ArrayList<Integer> stopIDList, ArrayList<Integer> userIDList, ArrayList<Integer> reservationNumList) {
        this.tripID = tripID;
        this.vehicleID = vehicleID;
        this.passengerIDList = passengerIDList;
        this.driverID = driverID;
        this.tripSchedID = tripSchedID;
        this.routeID = routeID;
        this.lineID = lineID;
        this.stopIDList = stopIDList;
        this.userIDList = userIDList;
        this.reservationNumList = reservationNumList;
    }

    protected KeyHandler(Parcel in) {
        this.tripID = in.readInt();
        this.vehicleID = in.readString();
        this.passengerIDList = in.readArrayList(Integer.class.getClassLoader());
        this.driverID = in.readInt();
        this.tripSchedID = in.readInt();
        this.routeID = in.readInt();
        this.lineID = in.readInt();
        this.stopIDList = in.readArrayList(Integer.class.getClassLoader());
        this.userIDList = in.readArrayList(Integer.class.getClassLoader());
        this.reservationNumList = in.readArrayList(Integer.class.getClassLoader());
    }

    public static final Creator<KeyHandler> CREATOR = new Creator<KeyHandler>() {
        @Override
        public KeyHandler createFromParcel(Parcel in) {
            return new KeyHandler(in);
        }

        @Override
        public KeyHandler[] newArray(int size) {
            return new KeyHandler[size];
        }
    };

    public int getTripID() {
        return tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public ArrayList<Integer> getStopIDList() {
        return stopIDList;
    }

    public void setStopIDList(ArrayList<Integer> stopIDList) {
        this.stopIDList = stopIDList;
    }

    public int getLineID() {
        return lineID;
    }

    public void setLineID(int lineID) {
        this.lineID = lineID;
    }

    public int getRouteID() {
        return routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public int getTripSchedID() {
        return tripSchedID;
    }

    public void setTripSchedID(int tripSchedID) {
        this.tripSchedID = tripSchedID;
    }

    public int getDriverID() {
        return driverID;
    }

    public ArrayList<Integer> getPassengerIDList() {
        return passengerIDList;
    }

    public void setPassengerIDList(ArrayList<Integer> passengerIDList) {
        this.passengerIDList = passengerIDList;
    }

    public String getVehicleID() {
        return vehicleID;
    }

    public ArrayList<Integer> getUserIDList() {
        return userIDList;
    }

    public void setUserIDList(ArrayList<Integer> userIDList) {
        this.userIDList = userIDList;
    }

    public ArrayList<Integer> getReservationNumList() {
        return reservationNumList;
    }

    public void setReservationNumList(ArrayList<Integer> reservationNumList) {
        this.reservationNumList = reservationNumList;
    }

    // following setters also overwrite the db

    public void setDriverID(int driverID, Context context) {
        this.driverID = driverID;
        dbHandler = new DBHandler(context);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Trip.COLUMN_TRIP_DRIVER, driverID);
        db.update(DBContract.Trip.TABLE_TRIP, cv, DBContract.Trip.COLUMN_TRIP_ID + "=" + getTripID(), null);
        db.close();
    }

    public void setVehicleID(String vehicleID, Context context) {
        this.vehicleID = vehicleID;
        dbHandler = new DBHandler(context);
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Trip.COLUMN_TRIP_VEHICLE, vehicleID);
        db.update(DBContract.Trip.TABLE_TRIP, cv, DBContract.Trip.COLUMN_TRIP_ID + "=" + getTripID(), null);
        db.close();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.tripID);
        parcel.writeString(this.vehicleID);
        parcel.writeList(this.passengerIDList);
        parcel.writeInt(this.driverID);
        parcel.writeInt(this.tripSchedID);
        parcel.writeInt(this.routeID);
        parcel.writeInt(this.lineID);
        parcel.writeList(this.stopIDList);
        parcel.writeList(this.userIDList);
        parcel.writeList(this.reservationNumList);
    }

    // handles the returning of string lists from db
    public ArrayList<String> getStringArrayListFromDB(Context context, String column, String table) {
        dbHandler = new DBHandler(context);
        ArrayList<String> temp = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] columns = {""};
        columns[0] = column;
        Cursor cursor = db.query(table,
                columns,
                null,
                null,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            temp = new ArrayList<>();
            do{
                temp.add(cursor.getString(cursor.getColumnIndex(column)));
            } while( cursor.moveToNext());
        }
        else{
            Log.e(TAG, "getStringArrayListFromDB() failed!");
        }
        cursor.close();
        db.close();
        return temp;
    }

    // handles the returning of string values from db
    public String getStringFromDB(Context context, String column, Object selected, String table, String id) {
        dbHandler = new DBHandler(context);
        String temp = null;
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] columns = {""}, selection = {""};
        columns[0] = column;
        selection[0] = String.valueOf(selected);
        Cursor cursor = db.query(table,
                columns,
                " " + id + " = ? ",
                selection,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            temp = cursor.getString(cursor.getColumnIndex(column));
        }
        else{
            Log.e(TAG, "getStringFromDB() failed!");
        }
        cursor.close();
        db.close();
        return temp;
    }

    // handles the returning of int values from db
    public int getIntFromDB(Context context, String column, Object selected, String table, String id) {
        dbHandler = new DBHandler(context);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] columns = {""}, selection = {""};
        int temp = -1 ;
        columns[0] = column;
        selection[0] = String.valueOf(selected);
        Cursor cursor = db.query(table,
                columns,
                " " + id + " = ? ",
                selection,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            temp =  cursor.getInt(cursor.getColumnIndex(column));
        }
        else{
            Log.e(TAG, "getIntFromDB() failed!");
        }
        cursor.close();
        db.close();
        return temp;
    }

    // handles the returning of double values from db
    public double getDoubleFromDB(Context context, String column, Object selected, String table, String id) {
        dbHandler = new DBHandler(context);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] columns = {""}, selection = {""};
        double temp = -1;
        columns[0] = column;
        selection[0] = String.valueOf(selected);
        Cursor cursor = db.query(table,
                columns,
                " " + id + " = ? ",
                selection,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            temp = cursor.getDouble(cursor.getColumnIndex(column));
        }
        else{
            Log.e(TAG, "getDoubleFromDB() failed!");
        }
        cursor.close();
        db.close();
        return temp;
    }

    // handles the returning of double values from db
    public boolean getBooleanFromDB(Context context, String column, Object selected, String table, String id) {
        dbHandler = new DBHandler(context);
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        String[] columns = {""}, selection = {""};
        boolean temp = false;
        columns[0] = column;
        selection[0] = String.valueOf(selected);
        Cursor cursor = db.query(table,
                columns,
                " " + id + " = ? ",
                selection,
                null,
                null,
                null);
        if(cursor.moveToFirst()){
            temp = cursor.getInt(cursor.getColumnIndex(column)) > 0;
        }
        else{
            Log.e(TAG, "getBooleanFromDB() failed!");
        }
        cursor.close();
        db.close();
        return temp;
    }

}
