package com.example.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.api.RetrofitClient;
import com.example.mobileproject.model.ForgotPasswordRequest;
import com.example.mobileproject.model.ForgotPasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reset_password_1 extends AppCompatActivity {
    private EditText emailEditText;
    private Button btnNext;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_1);

        emailEditText = findViewById(R.id.email);
        btnNext = findViewById(R.id.btnNext);

        // Khởi tạo ApiService từ RetrofitClient có sẵn
        apiService = RetrofitClient.getClient().create(ApiService.class);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                if (!email.isEmpty()) {
                    sendOTP(email);
                } else {
                    Toast.makeText(reset_password_1.this, 
                        "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void sendOTP(String email) {
        ForgotPasswordRequest request = new ForgotPasswordRequest(email);
        
        apiService.sendOTP(request).enqueue(new Callback<ForgotPasswordResponse>() {
            @Override
            public void onResponse(Call<ForgotPasswordResponse> call, 
                                 Response<ForgotPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ForgotPasswordResponse result = response.body();
                    if (result.isSuccess()) {
                        // Chuyển sang màn hình nhập OTP
                        Intent intent = new Intent(reset_password_1.this, 
                                                reset_password_2.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                        Toast.makeText(reset_password_1.this, 
                            result.getMessage(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(reset_password_1.this, 
                            result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponse> call, Throwable t) {
                Toast.makeText(reset_password_1.this, 
                    "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}