package com.example.mobileproject.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PotholeResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("severity")
    private String severity;

    @SerializedName("timestamp")
    private String timestamp;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("user_id")
    private int userId;


    public String getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getSeverity() {
        return severity;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getUserId() {
        return userId;
    }

    public String getCreatedAt() {
        return createdAt;
    }


    public PotholeData getData() {
        PotholeData data = new PotholeData(
                Integer.parseInt(id),
                latitude,
                longitude,
                severity,
                userId,
                null,
                createdAt != null ? new Date(timestamp * 1000) : null

        );
        return data;
    }
}