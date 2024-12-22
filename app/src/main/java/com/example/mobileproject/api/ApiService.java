package com.example.mobileproject.api;


import com.example.mobileproject.model.ForgotPasswordRequest;
import com.example.mobileproject.model.ForgotPasswordResponse;
import com.example.mobileproject.model.LoginRequest;
import com.example.mobileproject.model.LoginResponse;
import com.example.mobileproject.model.RegisterRequest;
import com.example.mobileproject.model.RegisterResponse;
import com.example.mobileproject.model.UpdatePasswordRequest;
import com.example.mobileproject.model.UpdatePasswordResponse;
import com.example.mobileproject.model.VerifyOtpRequest;
import com.example.mobileproject.model.VerifyOtpResponse;
import com.example.mobileproject.model.GoogleLoginRequest;
import com.example.mobileproject.model.GoogleLoginResponse;
import com.example.mobileproject.model.VerifyCurrentPasswordRequest;
import com.example.mobileproject.model.VerifyCurrentPasswordResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @POST("sign_up.php")
    @Headers("Content-Type: application/json")
    Call<RegisterResponse> registerUser(@Body RegisterRequest request);

    @POST("sign_in.php")
    @Headers("Content-Type: application/json")
    Call<LoginResponse> loginUser(@Body LoginRequest request);

    @POST("test_forget_password.php")
    @Headers("Content-Type: application/json")
    Call<ForgotPasswordResponse> sendOTP(@Body ForgotPasswordRequest request);

    @POST("verify_otp.php")
    @Headers("Content-Type: application/json")
    Call<VerifyOtpResponse> verifyOTP(@Body VerifyOtpRequest request);

    @POST("update_password.php")
    @Headers("Content-Type: application/json")
    Call<UpdatePasswordResponse> updatePassword(@Body UpdatePasswordRequest request);

    @POST("google_login.php")
    @Headers("Content-Type: application/json")
    Call<GoogleLoginResponse> googleLogin(@Body GoogleLoginRequest request);

    @POST("verify_current_password.php")
    @Headers("Content-Type: application/json")
    Call<VerifyCurrentPasswordResponse> verifyCurrentPassword(@Body VerifyCurrentPasswordRequest request);
}
