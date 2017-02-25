package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

public class Stop implements Parcelable{

    private int stopID;
    private String stopName;
    private String latitude;
    private String longitude;

    public Stop(int stopID, String stopName, String latitude, String longitude) {
        this.stopID = stopID;
        this.stopName = stopName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    protected Stop(Parcel in) {
        this.stopID = in.readInt();
        this.stopName = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
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

    public int getStopID() {
        return this.stopID;
    }

    public void setStopID(int stopID) {
        this.stopID = stopID;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getStopName() {
        return this.stopName;
    }

    public void setStopName(String stopName) {
        this.stopName = stopName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.stopID);
        parcel.writeString(this.stopName);
        parcel.writeString(this.latitude);
        parcel.writeString(this.longitude);
    }
}
