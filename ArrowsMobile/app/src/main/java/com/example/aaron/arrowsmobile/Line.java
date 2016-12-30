package com.example.aaron.arrowsmobile;


import android.os.Parcel;
import android.os.Parcelable;

public class Line implements Parcelable{

    private int lineNum;
    private String lineName;

    public Line(int lineNum, String lineName) {
        this.lineName = lineName;
        this.lineNum = lineNum;
    }

    protected Line(Parcel in) {
        this.lineNum = in.readInt();
        this.lineName = in.readString();
    }

    public static final Creator<Line> CREATOR = new Creator<Line>() {
        @Override
        public Line createFromParcel(Parcel in) {
            return new Line(in);
        }

        @Override
        public Line[] newArray(int size) {
            return new Line[size];
        }
    };

    public int getLineNum() {
        return this.lineNum;
    }

    public String getLineName() {
        return this.lineName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.lineNum);
        parcel.writeString(this.lineName);
    }
}
