package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

public class Driver implements Parcelable{

    private int driverID;
    private String lastName;
    private String firstName;
    private String nickName;

    public Driver(int driverID, String lastName, String firstName, String nickName) {
        this.driverID = driverID;
        this.lastName = lastName;
        this.firstName = firstName;
        this.nickName = nickName;
    }

    protected Driver(Parcel in) {
        this.driverID = in.readInt();
        this.lastName = in.readString();
        this.firstName = in.readString();
        this.nickName = in.readString();
    }

    public static final Creator<Driver> CREATOR = new Creator<Driver>() {
        @Override
        public Driver createFromParcel(Parcel in) {
            return new Driver(in);
        }

        @Override
        public Driver[] newArray(int size) {
            return new Driver[size];
        }
    };

    public int getDriverID() {
        return this.driverID;
    }

    public void setDriverID(int driverID) {
        this.driverID = driverID;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.driverID);
        parcel.writeString(this.lastName);
        parcel.writeString(this.firstName);
        parcel.writeString(this.nickName);
    }
}
