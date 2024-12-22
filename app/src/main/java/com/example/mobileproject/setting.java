package com.example.mobileproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class setting extends AppCompatActivity {
    Dialog dialog;
    Button btnLogout, btnCancel;
    private TextView fullNameTextView;
    private TextView emailTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        // Ánh xạ các RelativeLayout
        RelativeLayout profileLayout = findViewById(R.id.btnProfile);
        RelativeLayout logoutLayout = findViewById(R.id.imglogout);
        RelativeLayout changePassword = findViewById(R.id.btnChange);

        emailTextView = findViewById(R.id.edtEmail);
        fullNameTextView= findViewById(R.id.edtFull_name);
        // Lấy thông tin user từ SharedPreferences và hiển thị
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("full_name", "");
        String email = sharedPreferences.getString("email", "");

        if (!fullName.isEmpty()) {
            fullNameTextView.setText(fullName);
        }
        if (!email.isEmpty()) {
            emailTextView.setText(email);
        }

        // Khởi tạo dialog
        dialog = new Dialog(setting.this);
        dialog.setContentView(R.layout.logout);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.coner));
        dialog.setCancelable(false);

        // Ánh xạ các nút trong dialog
        btnLogout = dialog.findViewById(R.id.btnLogout);
        btnCancel = dialog.findViewById(R.id.btnCancel);

        // Xử lý sự kiện nút Cancel trong dialog
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // Đóng dialog
            }
        });

        ImageView home = findViewById(R.id.imageViewHome);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, dashboard.class);
                startActivity(intent);
            }
        });

        ImageView mapbox = findViewById(R.id.imageViewMap);
        mapbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, mapbox.class);
                startActivity(intent);
            }
        });


        // Xử lý sự kiện khi nhấn vào profileLayout
        profileLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleProfile();
            }
        });

        // Xử lý sự kiện khi nhấn vào changePassword
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, reset_your_password_1.class);
                startActivity(intent);
            }
        });

        // Hiển thị dialog khi nhấn vào logoutLayout
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
    }

    private void handleProfile() {
        Intent intent = new Intent(setting.this, profile.class);
        startActivity(intent);
    }
}
