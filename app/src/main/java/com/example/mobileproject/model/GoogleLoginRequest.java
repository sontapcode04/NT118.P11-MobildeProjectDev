package com.example.mobileproject.model;

public class GoogleLoginRequest {
    private String google_id;
    private String email;
    private String full_name;
    private String profile_pic;

    public GoogleLoginRequest(String google_id, String email, String full_name, String profile_pic) {
        this.google_id = google_id;
        this.email = email;
        this.full_name = full_name;
        this.profile_pic = profile_pic;
    }

    public String getGoogle_id() { return google_id; }
    public void setGoogle_id(String google_id) { this.google_id = google_id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFull_name() { return full_name; }
    public void setFull_name(String full_name) { this.full_name = full_name; }

    public String getProfile_pic() { return profile_pic; }
    public void setProfile_pic(String profile_pic) { this.profile_pic = profile_pic; }
} 