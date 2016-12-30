package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;

public class TripSched implements Parcelable{

    // entity fields
    private int tripSchedID;
    private String tripNum;
    private Calendar depTime;
    // relational fields
    private Route route;

    public TripSched(int tripSchedID, String tripNum, Calendar depTime, Route route) {
        this.tripSchedID = tripSchedID;
        this.tripNum = tripNum;
        this.depTime = depTime;
        this.route = route;
    }

    protected TripSched(Parcel in) {
        this.tripSchedID = in.readInt();
        this.tripNum = in.readString();
        this.depTime = (Calendar) in.readSerializable();
        this.route = in.readParcelable(Route.class.getClassLoader());
    }

    public static final Creator<TripSched> CREATOR = new Creator<TripSched>() {
        @Override
        public TripSched createFromParcel(Parcel in) {
            return new TripSched(in);
        }

        @Override
        public TripSched[] newArray(int size) {
            return new TripSched[size];
        }
    };

    public int getTripSchedID() {
        return this.tripSchedID;
    }

    public void setTripSchedID(int tripSchedID) {
        this.tripSchedID = tripSchedID;
    }

    public String getTripNum() {
        return this.tripNum;
    }

    public void setTripNum(String tripNum) {
        this.tripNum = tripNum;
    }

    public Calendar getDepTime() {
        return this.depTime;
    }

    public void setDepTime(Calendar depTime) {
        this.depTime = depTime;
    }

    public Route getRoute() {
        return this.route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.tripSchedID);
        parcel.writeString(this.tripNum);
        parcel.writeSerializable(this.depTime);
        parcel.writeParcelable(this.route, 0);
    }
}
