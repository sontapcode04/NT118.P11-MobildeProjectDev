package com.example.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.model.UpdatePasswordRequest;
import com.example.mobileproject.model.UpdatePasswordResponse;
import com.example.mobileproject.api.RetrofitClient;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class set_your_password extends AppCompatActivity {
    private EditText passwordEditText, confirmPasswordEditText;
    private Button btnUpdate;
    private ImageView backButton;
    private String email;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_your_password);

        // Nhận email từ màn hình trước
        email = getIntent().getStringExtra("EMAIL");

        // Ánh xạ view
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirmpassword);
        btnUpdate = findViewById(R.id.btnupdate);
        backButton = findViewById(R.id.back);

        // Khởi tạo ApiService
        apiService = RetrofitClient.getClient().create(ApiService.class);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = passwordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(set_your_password.this, 
                        "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(set_your_password.this, 
                        "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                updatePassword(email, password);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void updatePassword(String email, String password) {
        UpdatePasswordRequest request = new UpdatePasswordRequest(email, password);
        
        apiService.updatePassword(request).enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, 
                                 Response<UpdatePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UpdatePasswordResponse result = response.body();
                    if (result.isSuccess()) {
                        Toast.makeText(set_your_password.this, 
                            "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        // Chuyển về màn hình đăng nhập
                        Intent intent = new Intent(set_your_password.this, 
                                                password_reset_successful.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | 
                                      Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(set_your_password.this, 
                            result.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                Toast.makeText(set_your_password.this, 
                    "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
