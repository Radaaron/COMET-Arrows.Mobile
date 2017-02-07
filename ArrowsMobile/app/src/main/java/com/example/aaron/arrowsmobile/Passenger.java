package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

public class Passenger implements Parcelable{

    private int passengerID;
    private String feedbackOn;
    private int feedback;
    private String tapIn;
    private String tapOut;
    private String disembarkationPt;
    private String destination;
    private boolean isChance;

    public Passenger(int passengerID, String feedbackOn, int feedback, String tapIn, String tapOut, String disembarkationPt, String destination, Boolean isChance) {
        this.passengerID = passengerID;
        this.feedbackOn = feedbackOn;
        this.feedback = feedback;
        this.tapIn = tapIn;
        this.tapOut = tapOut;
        this.disembarkationPt = disembarkationPt;
        this.destination = destination;
        this.isChance = isChance;
    }

    protected Passenger(Parcel in) {
        this.passengerID = in.readInt();
        this.feedbackOn = in.readString();
        this.feedback = in.readInt();
        this.tapIn = (String) in.readSerializable();
        this.tapOut = (String) in.readSerializable();
        this.disembarkationPt = in.readString();
        this.destination = in.readString();
        this.isChance = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeInt(this.passengerID);
        parcel.writeString(this.feedbackOn);
        parcel.writeInt(this.feedback);
        parcel.writeSerializable(this.tapIn);
        parcel.writeSerializable(this.tapOut);
        parcel.writeString(this.disembarkationPt);
        parcel.writeString(this.destination);
        parcel.writeByte((byte) (this.isChance ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Passenger> CREATOR = new Creator<Passenger>() {
        @Override
        public Passenger createFromParcel(Parcel in) {
            return new Passenger(in);
        }

        @Override
        public Passenger[] newArray(int size) {
            return new Passenger[size];
        }
    };

    public int getPassengerID() {
        return this.passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public String getDestination() {
        return this.destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public boolean isChance() {
        return this.isChance;
    }

    public void setChance(boolean chance) {
        this.isChance = chance;
    }

    public String getDisembarkationPt() {
        return this.disembarkationPt;
    }

    public void setDisembarkationPt(String disembarkationPt) {
        this.disembarkationPt = disembarkationPt;
    }

    public String getTapOut() {
        return this.tapOut;
    }

    public void setTapOut(String tapOut) {
        this.tapOut = tapOut;
    }

    public String getTapIn() {
        return this.tapIn;
    }

    public void setTapIn(String tapIn) {
        this.tapIn = tapIn;
    }

    public int getFeedback() {
        return this.feedback;
    }

    public void setFeedback(int feedback) {
        this.feedback = feedback;
    }

    public String getFeedbackOn() {
        return this.feedbackOn;
    }

    public void setFeedbackOn(String feedbackOn) {
        this.feedbackOn = feedbackOn;
    }
}
