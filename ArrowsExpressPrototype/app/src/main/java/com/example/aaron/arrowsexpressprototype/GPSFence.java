package com.example.aaron.arrowsexpressprototype;

public class GPSFence {

    private String areaLabel;
    private double xOrigin, yOrigin, offsetRadius;

    public GPSFence(String areaLabel, double xOrigin, double yOrigin, double offsetRadius) {
        this.areaLabel = areaLabel;
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.offsetRadius = offsetRadius;
    }

    public String getAreaLabel() {
        return this.areaLabel;
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
