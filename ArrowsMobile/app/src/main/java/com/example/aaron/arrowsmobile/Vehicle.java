package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

public class Vehicle implements Parcelable{

    private String vehicleID;
    private String vehicleType;
    private int capacity;
    private String image;
    private String plateNum;
    private String model;
    private String brand;

    public Vehicle(String vehicleID, String vehicleType, int capacity, String image, String plateNum, String model, String brand) {
        this.vehicleID = vehicleID;
        this.vehicleType = vehicleType;
        this.capacity = capacity;
        this.image = image;
        this.plateNum = plateNum;
        this.model = model;
        this.brand = brand;
    }

    protected Vehicle(Parcel in) {
        this.vehicleID = in.readString();
        this.vehicleType = in.readString();
        this.capacity = in.readInt();
        this.image = in.readString();
        this.plateNum = in.readString();
        this.model = in.readString();
        this.brand = in.readString();
    }

    public static final Creator<Vehicle> CREATOR = new Creator<Vehicle>() {
        @Override
        public Vehicle createFromParcel(Parcel in) {
            return new Vehicle(in);
        }

        @Override
        public Vehicle[] newArray(int size) {
            return new Vehicle[size];
        }
    };

    public String getVehicleID() {
        return this.vehicleID;
    }

    public void setVehicleID(String vehicleID) {
        this.vehicleID = vehicleID;
    }

    public String getBrand() {
        return this.brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPlateNum() {
        return this.plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getImage() {
        return this.image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCapacity() {
        return this.capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getVehicleType() {
        return this.vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.vehicleID);
        parcel.writeString(this.vehicleType);
        parcel.writeInt(this.capacity);
        parcel.writeString(this.image);
        parcel.writeString(this.plateNum);
        parcel.writeString(this.model);
        parcel.writeString(this.brand);
    }
}
