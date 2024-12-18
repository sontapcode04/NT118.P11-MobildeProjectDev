package com.example.mobileproject.model;
import com.google.gson.annotations.SerializedName;

public class detectedPotholeRequest {
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longitude")
    private double longitude;
    @SerializedName("id")
    private int id;
    @SerializedName("severity")
    private String severity;

    public detectedPotholeRequest(int id, double latitude, double longitude, String severity) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
        this.severity = severity;
    }
}