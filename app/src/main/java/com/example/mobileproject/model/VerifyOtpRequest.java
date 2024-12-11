package com.example.mobileproject.model;
public class VerifyOtpRequest {
    private String email;
    private String OTP;

    public VerifyOtpRequest(String email, String OTP) {
        this.email = email;
        this.OTP = OTP;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOTP() {
        return OTP;
    }

    public void setOTP(String OTP) {
        this.OTP = OTP;
    }
} 