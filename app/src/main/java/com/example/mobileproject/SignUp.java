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
import com.example.mobileproject.api.RetrofitClient;
import com.example.mobileproject.model.RegisterRequest;
import com.example.mobileproject.model.RegisterResponse;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {
    private EditText fullnameEdit, emailEdit, passwordEdit, confirmPasswordEdit;
    private Button sign_up_button;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);

        //@@@@@@@@@@@@@@@@@@@@@@@
        TextView tvSignIn = findViewById(R.id.signin);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Signin.class);
                startActivity(intent);
            }
        });

        ImageView back = findViewById(R.id.backToSignIn);
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Signin.class);
                startActivity(intent);
            }
        });
        ImageView backButton = findViewById(R.id.backToSignIn);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Signin.class);
                startActivity(intent);
                finish();
            }
        });
        //@@@@@@@@@@@@@@@@@@@@@@@@@@
        // Sử dụng RetrofitClient thay vì khởi tạo trực tiếp
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Ánh xạ views
        fullnameEdit = findViewById(R.id.fullname);
        emailEdit = findViewById(R.id.email);
        passwordEdit = findViewById(R.id.password);
        confirmPasswordEdit = findViewById(R.id.confirmpassword);
        sign_up_button = findViewById(R.id.sign_up_button);
        sign_up_button.setOnClickListener(v -> registerUser());
    }
    private void registerUser() {
        // Lấy dữ liệu từ EditText
        String fullname = fullnameEdit.getText().toString().trim();
        String email = emailEdit.getText().toString().trim();
        String password = passwordEdit.getText().toString().trim();
        String confirmPassword = confirmPasswordEdit.getText().toString().trim();

        // Validate dữ liệu
        if (fullname.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Tạo object request
        RegisterRequest request = new RegisterRequest(fullname, email, password);

        // Gọi API
        apiService.registerUser(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RegisterResponse registerResponse = response.body();
                    Toast.makeText(SignUp.this,
                            registerResponse.getMessage(), Toast.LENGTH_SHORT).show();

                    if ("success".equals(registerResponse.getStatus())) {
                        // Xử lý khi đăng ký thành công
                        finish(); // Hoặc chuyển sang màn hình khác
                    }
                } else {
                    try {
                        // Đọc error message từ response
                        String errorBody = response.errorBody().string();
                        Toast.makeText(SignUp.this,
                                "Error: " + errorBody, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(SignUp.this,
                        "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

