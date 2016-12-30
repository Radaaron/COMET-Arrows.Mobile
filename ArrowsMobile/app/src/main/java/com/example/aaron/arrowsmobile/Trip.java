package com.example.aaron.arrowsmobile;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Calendar;


public class Trip implements Parcelable{

    // entity fields
    private int tripID;
    private String remarks;
    private Calendar tripDate;
    private Calendar depTime;
    private Calendar arrivalTime;
    private double duration;
    private boolean isSpecial;
    private int spNumPass;
    private String purpose;
    // relational fields
    private TripSched tripSched;
    private Vehicle vehicle;
    private Driver driver;
    private ArrayList<Passenger> passengerList;

    public Trip(int tripID, String remarks, Calendar tripDate, Calendar depTime, Calendar arrivalTime, double duration, boolean isSpecial, int spNumPass, String purpose, TripSched tripSched, Vehicle vehicle, Driver driver, ArrayList<Passenger> passengerList) {
        this.tripID = tripID;
        this.remarks = remarks;
        this.tripDate = tripDate;
        this.depTime = depTime;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.isSpecial = isSpecial;
        this.spNumPass = spNumPass;
        this.purpose = purpose;
        this.tripSched = tripSched;
        this.vehicle = vehicle;
        this.driver = driver;
        this.passengerList = passengerList;
    }

    protected Trip(Parcel in) {
        this.tripID = in.readInt();
        this.remarks = in.readString();
        this.tripDate = (Calendar) in.readSerializable();
        this.depTime = (Calendar) in.readSerializable();
        this.arrivalTime = (Calendar) in.readSerializable();
        this.duration = in.readDouble();
        this.isSpecial = in.readByte() != 0;
        this.spNumPass = in.readInt();
        this.purpose = in.readString();
        this.tripSched = in.readParcelable(TripSched.class.getClassLoader());
        this.vehicle = in.readParcelable(Vehicle.class.getClassLoader());
        this.driver = in.readParcelable(Driver.class.getClassLoader());
        this.passengerList = in.readArrayList(Passenger.class.getClassLoader());
    }

    public static final Creator<Trip> CREATOR = new Creator<Trip>() {
        @Override
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }

        @Override
        public Trip[] newArray(int size) {
            return new Trip[size];
        }
    };

    public int getTripID() {
        return this.tripID;
    }

    public void setTripID(int tripID) {
        this.tripID = tripID;
    }

    public Driver getDriver() {
        return this.driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public TripSched getTripSched() {
        return this.tripSched;
    }

    public void setTripSched(TripSched tripSched) {
        this.tripSched = tripSched;
    }

    public String getPurpose() {
        return this.purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public int getSpNumPass() {
        return this.spNumPass;
    }

    public void setSpNumPass(int spNumPass) {
        this.spNumPass = spNumPass;
    }

    public boolean isSpecial() {
        return this.isSpecial;
    }

    public void setSpecial(boolean special) {
        this.isSpecial = special;
    }

    public double getDuration() {
        return this.duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public Calendar getArrivalTime() {
        return this.arrivalTime;
    }

    public void setArrivalTime(Calendar arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Calendar getDepTime() {
        return this.depTime;
    }

    public void setDepTime(Calendar depTime) {
        this.depTime = depTime;
    }

    public Calendar getTripDate() {
        return this.tripDate;
    }

    public void setTripDate(Calendar tripDate) {
        this.tripDate = tripDate;
    }

    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public void addToPassengerList(Passenger passenger){
        this.passengerList.add(passenger);
    }

    public Passenger getPassenger(int index){
        return this.passengerList.get(index);
    }

    public int getPassengerCount(){
        return this.passengerList.size();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.tripID);
        parcel.writeString(this.remarks);
        parcel.writeSerializable(this.tripDate);
        parcel.writeSerializable(this.depTime);
        parcel.writeSerializable(this.arrivalTime);
        parcel.writeDouble(this.duration);
        parcel.writeByte((byte) (this.isSpecial ? 1 : 0));
        parcel.writeInt(this.spNumPass);
        parcel.writeString(this.purpose);
        parcel.writeParcelable(this.tripSched, 0);
        parcel.writeParcelable(this.vehicle, 0);
        parcel.writeParcelable(this.driver, 0);
        parcel.writeList(this.passengerList);
    }
}
