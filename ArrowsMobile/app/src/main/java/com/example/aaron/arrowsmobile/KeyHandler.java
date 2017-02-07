package com.example.aaron.arrowsmobile;

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
    private int driverID;
    private int tripSchedID;
    private int routeID;
    private int lineID;
    private ArrayList<Integer> stopIDList;

    private DBHandler dbHandler;

    public KeyHandler() {
        // empty constructor for method use only
    }

    public KeyHandler(int tripID, String vehicleID, ArrayList<Integer> passengerIDList, int driverID, int tripSchedID, int routeID, int lineID, ArrayList<Integer> stopIDList) {
        this.tripID = tripID;
        this.vehicleID = vehicleID;
        this.passengerIDList = passengerIDList;
        this.driverID = driverID;
        this.tripSchedID = tripSchedID;
        this.routeID = routeID;
        this.lineID = lineID;
        this.stopIDList = stopIDList;
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

    public void setDriverID(int driverID) {
        this.driverID = driverID;
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

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
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
    }

    // refreshes list of passengerID's in case of a chance passenger
    public void refreshPassengerIDList(){
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.query(DBContract.Passenger.TABLE_PASSENGER, null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            do{
                if(!(passengerIDList.contains(cursor.getInt(cursor.getColumnIndex(DBContract.Passenger.COLUMN_PASSENGER_ID))))) {
                    passengerIDList.add(cursor.getInt(cursor.getColumnIndex(DBContract.Passenger.COLUMN_PASSENGER_ID)));
                }
            }while(cursor.moveToNext());
        }
        else{
            Log.e(TAG, "passengerIDList refresh failed!");
        }
        cursor.close();
        db.close();
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
            Log.e(TAG, "getIntFromDB() failed!");
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
            Log.e(TAG, "getIntFromDB() failed!");
        }
        cursor.close();
        db.close();
        return temp;
    }

}
