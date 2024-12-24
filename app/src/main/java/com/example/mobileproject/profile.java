package com.example.mobileproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class profile extends AppCompatActivity {
    private EditText fullNameEditText;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Ánh xạ các EditText
        fullNameEditText = findViewById(R.id.editpassword);  // EditText cho full name
        emailEditText = findViewById(R.id.editcfpassword);   // EditText cho email

        // Hiển thị thông tin user
        displayUserInfo();

        // Back button handler
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayUserInfo() {
        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("full_name", "");
        String email = sharedPreferences.getString("email", "");

        // Hiển thị thông tin
        fullNameEditText.setText(fullName);
        emailEditText.setText(email);
    }
}