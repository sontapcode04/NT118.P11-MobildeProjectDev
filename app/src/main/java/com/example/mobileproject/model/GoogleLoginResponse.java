package com.example.mobileproject.model;

public class GoogleLoginResponse {
    private String status;
    private String message;
    private UserData data;

    public static class UserData {
        private int id;
        private String email;
        private String full_name;
        private String login_type;

        // Getters and setters
        public int getId() { return id; }
        public String getEmail() { return email; }
        public String getFullName() { return full_name; }
        public String getLoginType() { return login_type; }
    }

    // Getters and setters
    public String getStatus() { return status; }
    public String getMessage() { return message; }
    public UserData getData() { return data; }
} 