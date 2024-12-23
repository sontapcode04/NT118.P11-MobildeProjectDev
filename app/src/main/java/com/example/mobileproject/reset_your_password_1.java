package com.example.mobileproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.model.VerifyCurrentPasswordRequest;
import com.example.mobileproject.model.VerifyCurrentPasswordResponse;
import com.example.mobileproject.api.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reset_your_password_1 extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button btnNext;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_your_password_1_1);

        // Initialize views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.editText);
        btnNext = findViewById(R.id.btnNext);

        // Lấy thông tin từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String emailinput = sharedPreferences.getString("email", "");

        // Hiển thị thông tin
        emailEditText.setText(emailinput);


        // Initialize Retrofit
        apiService = RetrofitClient.getClient().create(ApiService.class);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(reset_your_password_1.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                verifyCurrentPassword(email, password);
            }
        });

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
        String emailinput = sharedPreferences.getString("email", "");

        // Hiển thị thông tin
        emailEditText.setText(emailinput);
    }

    private void verifyCurrentPassword(String email, String password) {
        VerifyCurrentPasswordRequest request = new VerifyCurrentPasswordRequest(email, password);

        apiService.verifyCurrentPassword(request).enqueue(new Callback<VerifyCurrentPasswordResponse>() {
            @Override
            public void onResponse(Call<VerifyCurrentPasswordResponse> call, Response<VerifyCurrentPasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        // Password verified successfully
                        Intent intent = new Intent(reset_your_password_1.this, reset_your_password_2.class);
                        intent.putExtra("email", email);
                        startActivity(intent);
                    } else {
                        Toast.makeText(reset_your_password_1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(reset_your_password_1.this, "Error verifying password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VerifyCurrentPasswordResponse> call, Throwable t) {
                Toast.makeText(reset_your_password_1.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}