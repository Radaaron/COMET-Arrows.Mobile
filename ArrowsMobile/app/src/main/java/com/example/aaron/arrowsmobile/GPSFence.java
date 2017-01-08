package com.example.aaron.arrowsmobile;

public class GPSFence {

    private String stopName;
    private double xOrigin, yOrigin, offsetRadius;

    public GPSFence(String stopName, double xOrigin, double yOrigin, double offsetRadius) {
        this.stopName = stopName;
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.offsetRadius = offsetRadius;
    }

    public String getStopName() {
        return this.stopName;
    }

    public double getxOrigin() {
        return this.xOrigin;
    }

    public double getyOrigin() {
        return this.yOrigin;
    }

    public double getoffsetRadius() {
        return this.offsetRadius;
    }

}

