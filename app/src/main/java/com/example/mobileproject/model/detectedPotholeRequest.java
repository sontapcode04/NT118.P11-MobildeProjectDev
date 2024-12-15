package com.example.mobileproject.model;
import com.google.gson.annotations.SerializedName;

public class detectedPotholeRequest {
    @SerializedName("latitude")
    private double latitude;
    @SerializedName("longtitude")
    private double longtitude;
    @SerializedName("id")
    private int id;
    @SerializedName("severity")
    private String severity;

    public detectedPotholeRequest(int id, double latitude, double longtitude, String severity) {
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.id = id;
        this.severity = severity;
    }
}