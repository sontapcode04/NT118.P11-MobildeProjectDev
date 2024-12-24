package com.example.mobileproject.model;

public class VerifyCurrentPasswordRequest {
    private String email;
    private String password;

    public VerifyCurrentPasswordRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}