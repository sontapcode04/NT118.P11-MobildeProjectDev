package com.example.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.api.RetrofitClient;
import com.example.mobileproject.model.UpdatePasswordRequest;
import com.example.mobileproject.model.UpdatePasswordResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class reset_your_password_2 extends AppCompatActivity {
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    private Button btnUpdatePassword;
    private ApiService apiService;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reset_your_password_2_2);

        // Get email from previous screen
        email = getIntent().getStringExtra("email");

        // Initialize views
        newPasswordEditText = findViewById(R.id.editpassword);
        confirmPasswordEditText = findViewById(R.id.editcfpassword);
        btnUpdatePassword = findViewById(R.id.btnUpdate_password);

        // Initialize Retrofit
        apiService = RetrofitClient.getClient().create(ApiService.class);

        btnUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newPasswordEditText.getText().toString().trim();
                String confirmPassword = confirmPasswordEditText.getText().toString().trim();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(reset_your_password_2.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(reset_your_password_2.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                updatePassword(email, newPassword);
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

    private void updatePassword(String email, String newPassword) {
        UpdatePasswordRequest request = new UpdatePasswordRequest(email, newPassword);

        apiService.updatePassword(request).enqueue(new Callback<UpdatePasswordResponse>() {
            @Override
            public void onResponse(Call<UpdatePasswordResponse> call, Response<UpdatePasswordResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(reset_your_password_2.this, "Password updated successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(reset_your_password_2.this, setting.class);
                        startActivity(intent);
                        finishAffinity(); // Close all previous activities
                    } else {
                        Toast.makeText(reset_your_password_2.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(reset_your_password_2.this, "Error updating password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdatePasswordResponse> call, Throwable t) {
                Toast.makeText(reset_your_password_2.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}