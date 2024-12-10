package com.example.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.model.VerifyOtpRequest;
import com.example.mobileproject.model.VerifyOtpResponse;
import com.example.mobileproject.api.RetrofitClient;

import com.example.setpassword.setpassword;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reset_password_2 extends AppCompatActivity {
    private EditText numberCodeEditText;
    private Button updateButton;
    private TextView tvEmail;
    private String email;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_password_2);

        // Nhận email từ màn hình trước
        email = getIntent().getStringExtra("EMAIL");
        
        numberCodeEditText = findViewById(R.id.numbercode);
        updateButton = findViewById(R.id.update_button);
        tvEmail = findViewById(R.id.tvEmail);

        tvEmail.setText(email);

        // Khởi tạo ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = numberCodeEditText.getText().toString().trim();
                if (!otp.isEmpty()) {
                    verifyOTP(email, otp);
                } else {
                    Toast.makeText(reset_password_2.this, 
                        "Vui lòng nhập mã xác thực", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyOTP(String email, String otp) {
        VerifyOtpRequest request = new VerifyOtpRequest(email, otp);
        
        apiService.verifyOTP(request).enqueue(new Callback<VerifyOtpResponse>() {
            @Override
            public void onResponse(Call<VerifyOtpResponse> call, 
                                 Response<VerifyOtpResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    VerifyOtpResponse result = response.body();
                    if (result.isSuccess()) {
                        // OTP hợp lệ, chuyển sang màn hình đổi mật khẩu
                        Intent intent = new Intent(reset_password_2.this, 
                                                set_your_password.class);
                        intent.putExtra("EMAIL", email);
                        startActivity(intent);
                        finish(); // Đóng màn hình hiện tại
                    } else {
                        Toast.makeText(reset_password_2.this, 
                            result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<VerifyOtpResponse> call, Throwable t) {
                Toast.makeText(reset_password_2.this, 
                    "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
