package com.example.mobileproject.model;

public class User {
    private int id;
    private String full_name;
    private String email;

    public User(int id, String full_name, String email) {
        this.id = id;
        this.full_name = full_name;
        this.email = email;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return full_name;
    }

    public void setFullName(String full_name) {
        this.full_name = full_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
