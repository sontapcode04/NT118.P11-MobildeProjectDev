package com.example.mobileproject.model;

import com.google.gson.annotations.SerializedName;

public class detectedPotholeResponse {
    @SerializedName("status")
    private String status;

    @SerializedName("message")
    private String message;

    // Constructor
    public detectedPotholeResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    // Getters
    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
