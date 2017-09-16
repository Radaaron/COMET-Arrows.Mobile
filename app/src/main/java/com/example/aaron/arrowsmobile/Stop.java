package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

public class Stop implements Parcelable{

    private int stopID;
    private String stopName;
    private double latitude;
    private double longitude;

    public Stop(int stopID, String stopName, double latitude, double longitude) {
        this.stopID = stopID;
        this.stopName = stopName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Stop(Parcel in) {
        this.stopID = in.readInt();
        this.stopName = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Creator<Stop> CREATOR = new Creator<Stop>() {
        @Override
        public Stop createFromParcel(Parcel in) {
            return new Stop(in);
        }

        @Override
        public Stop[] newArray(int size) {
            return new Stop[size];
        }
    };

    public double getLongitude() {
        return this.longitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public String getStopName() {
        return this.stopName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.stopID);
        parcel.writeString(this.stopName);
        parcel.writeDouble(this.latitude);
        parcel.writeDouble(this.longitude);
    }
}
