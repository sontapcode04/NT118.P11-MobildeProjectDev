package com.example.mobileproject.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

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
    private long timestamp;

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