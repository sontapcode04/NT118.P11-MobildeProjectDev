package com.example.mobileproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.mobileproject.model.LoginRequest;
import com.example.mobileproject.model.LoginResponse;
import com.example.mobileproject.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signin extends AppCompatActivity {
    private EditText emailEditText;
    private EditText passwordEditText;
    private Button btnLogin;
    private TextView btnRegister;
    private ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_activity);

        TextView tvSignUp = findViewById(R.id.sign_up);
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, SignUp.class);
                startActivity(intent);
            }
        });
        ImageView backButton = findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, welcome.class);
                startActivity(intent);
                finish();
            }
        });

        TextView tvForgotpassword = findViewById(R.id.forgot_password);
        tvForgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin.this, reset_password_1.class);
                startActivity(intent);
            }
        });
        //@@@@@@@@@@@@@@@

        // Khởi tạo ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        // Ánh xạ views
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        btnLogin = findViewById(R.id.sign_in_button);

        // Xử lý đăng nhập
        btnLogin.setOnClickListener(view -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (validateInput(email, password)) {
                performLogin(email, password);
            }
        });
    }
    private boolean validateInput(String email, String password) {
        if (email.isEmpty()) {
            emailEditText.setError("Email không được để trống");
            return false;
        }
        if (password.isEmpty()) {
            passwordEditText.setError("Mật khẩu không được để trống");
            return false;
        }
        return true;
    }

    private void performLogin(String email, String password) {
        LoginRequest loginRequest = new LoginRequest(email, password);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang đăng nhập...");
        progressDialog.show();

        apiService.loginUser(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();
                    if ("success".equals(loginResponse.getStatus())) {
                        // Lưu thông tin user
                        saveUserInfo(loginResponse.getUser());

                        Toast.makeText(Signin.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                        // Chuyển màn hình
                        Intent intent = new Intent(Signin.this, dashboard.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(Signin.this, loginResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Signin.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Signin.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveUserInfo(User user) {
        if (user != null) {
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("user_id", user.getId());
            editor.putString("full_name", user.getFullName());
            editor.putString("email", user.getEmail());
            editor.apply();
        }
    }
}