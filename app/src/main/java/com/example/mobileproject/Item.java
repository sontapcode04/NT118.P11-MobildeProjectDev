package com.example.mobileproject;

public class Item {

    public Item(double latitude, double longitude, String createAt, int severity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.createAt = createAt;
        this.severity = severity;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getSeverity() {
        return severity;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setSeverity(int severity) {
        this.severity = severity;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    double latitude, longitude;
    int severity;
    String createAt;
}
