package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Route implements Parcelable{

    // entity fields
    private int routeID;
    private String routeName;
    private String routeDescription;
    // relational fields
    private Line line;
    private ArrayList<RouteStop> routeStopList;

    public Route(int routeID, String routeName, String routeDescription, Line line, ArrayList<RouteStop> routeStopList) {
        this.routeID = routeID;
        this.line = line;
        this.routeDescription = routeDescription;
        this.routeName = routeName;
        this.routeStopList = routeStopList;
    }

    protected Route(Parcel in) {
        this.routeID = in.readInt();
        this.routeName = in.readString();
        this.routeDescription = in.readString();
        this.line = in.readParcelable(Line.class.getClassLoader());
        this.routeStopList = in.readArrayList(RouteStop.class.getClassLoader());
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    public int getRouteID() {
        return this.routeID;
    }

    public void setRouteID(int routeID) {
        this.routeID = routeID;
    }

    public Line getLine() {
        return this.line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getRouteDescription() {
        return this.routeDescription;
    }

    public void setRouteDescription(String routeDescription) {
        this.routeDescription = routeDescription;
    }

    public String getRouteName() {
        return this.routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public void addToRouteStopList(RouteStop routeStop){
        this.routeStopList.add(routeStop);
    }

    public RouteStop getRouteStop(int index){
        return this.routeStopList.get(index);
    }

    public ArrayList<RouteStop> getRouteStopList(){
        return this.routeStopList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.routeID);
        parcel.writeString(this.routeName);
        parcel.writeString(this.routeDescription);
        parcel.writeParcelable(this.line, 0);
        parcel.writeList(this.routeStopList);
    }
}
