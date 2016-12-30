package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

public class RouteStop implements Parcelable{

    // entity fields
    private String stopNum;
    private int order;
    // relational field
    private Stop stop;

    public RouteStop(String stopNum, int order, Stop stop) {
        this.stopNum = stopNum;
        this.order = order;
        this.stop = stop;
    }

    protected RouteStop(Parcel in) {
        this.stopNum = in.readString();
        this.order = in.readInt();
        this.stop = in.readParcelable(Stop.class.getClassLoader());
    }

    public static final Creator<RouteStop> CREATOR = new Creator<RouteStop>() {
        @Override
        public RouteStop createFromParcel(Parcel in) {
            return new RouteStop(in);
        }

        @Override
        public RouteStop[] newArray(int size) {
            return new RouteStop[size];
        }
    };

    public String getStopNum() {
        return this.stopNum;
    }

    public void setStopNum(String stopNum) {
        this.stopNum = stopNum;
    }

    public Stop getStop() {
        return this.stop;
    }

    public void setStop(Stop stop) {
        this.stop = stop;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.stopNum);
        parcel.writeInt(this.order);
        parcel.writeParcelable(this.stop, 0);
    }
}
