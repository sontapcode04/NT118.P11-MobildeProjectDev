package com.example.mobileproject;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class setting extends AppCompatActivity {
    Dialog dialog;
    Button btnLogout, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Log.d("LifecycleSetting", "Setting onCreate called");
        // Thêm code để hiển thị thông tin user
        displayUserInfo();

        // Ánh xạ các RelativeLayout
        RelativeLayout profileLayout = findViewById(R.id.btnProfile);
        RelativeLayout logoutLayout = findViewById(R.id.imglogout);
        RelativeLayout changePassword = findViewById(R.id.btnChange);
        RelativeLayout languageLayout = findViewById(R.id.btnChange_Language);

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
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
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
        languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleLanguage();
            }
        });
    }

    private void handleProfile() {
        Intent intent = new Intent(setting.this, profile.class);
        startActivity(intent);
    }

    private void displayUserInfo() {
        // Tìm TextView trong layout
        TextView fullNameTextView = findViewById(R.id.edtFull_name);
        TextView emailTextView = findViewById(R.id.edtEmail);

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("full_name", "User");
        String email = sharedPreferences.getString("email", "");

        // Hiển thị thông tin
        fullNameTextView.setText(fullName);
        emailTextView.setText(email);
    }

//    private void toggleLanguage() {
//        String currentLang = getResources().getConfiguration().locale.getLanguage();
//
//        Locale newLocale;
//        if (currentLang.equals("vi")) {
//            newLocale = new Locale("en");
//        } else {
//            newLocale = new Locale("vi");
//        }
//
//        Locale.setDefault(newLocale);
//        Configuration config = new Configuration();
//        config.setLocale(newLocale);
//        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
//
//        Intent intent = getIntent();
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//    }

    private void toggleLanguage() {
        String currentLang = getResources().getConfiguration().locale.getLanguage();

        Locale newLocale;
        if (currentLang.equals("vi")) {
            newLocale = new Locale("en");
        } else {
            newLocale = new Locale("vi");
        }

        Locale.setDefault(newLocale);
        Configuration config = new Configuration();
        config.setLocale(newLocale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        // Tạo intent để reload ứng dụng
        Intent intent = new Intent(this, setting.class); // MainActivity là activity khởi động chính
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        // Khởi chạy lại activity
        startActivity(intent);

        // Kết thúc ứng dụng hiện tại
        finish();
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.d("LifecycleSetting", "Setting onPause called");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("LifecycleSetting", "Setting onRestart called");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("LifecycleSetting", "Setting onResume called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("LifecycleSetting", "Setting onStart called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("LifecycleSetting", "Setting onStop called");
    }
}
