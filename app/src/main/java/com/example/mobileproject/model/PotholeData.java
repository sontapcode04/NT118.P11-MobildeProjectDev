package com.example.mobileproject.model;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class PotholeData {
    @SerializedName("latitude")
    private double latitude;

    @SerializedName("longitude")
    private double longitude;

    @SerializedName("severity")
    private String severity;

    @SerializedName("user_id")
    private int userId;

    private Integer id;

    @SerializedName("created_at")
    private Date createdAt;

    public PotholeData(Integer id, double latitude, double longitude, String severity, int userId) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.severity = severity;
        this.userId = userId;
    }

    public PotholeData(Integer id, double latitude, double longitude, String severity, int userId, Date createdAt) {
        this(id, latitude, longitude, severity, userId);
        this.createdAt = createdAt;
    }

    public Integer getId() {
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

    public int getUserId() {
        return userId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

}
