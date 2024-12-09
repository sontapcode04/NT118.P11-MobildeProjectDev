package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class set_your_password extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_your_password_2);

        ImageView backButton = findViewById(R.id.back); // Ánh xạ ImageView
        Button btnUpdate = findViewById(R.id.btnupdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(set_your_password.this, password_reset_successful.class);
                startActivity(intent);
                finish(); // Kết thúc activity hiện tại sau khi chuyển màn hình
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
