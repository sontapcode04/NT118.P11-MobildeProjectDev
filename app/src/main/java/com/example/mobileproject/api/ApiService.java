package com.example.mobileproject.api;


import com.example.mobileproject.model.ApiResponse;
import com.example.mobileproject.model.ForgotPasswordRequest;
import com.example.mobileproject.model.ForgotPasswordResponse;
import com.example.mobileproject.model.LoginRequest;
import com.example.mobileproject.model.LoginResponse;
import com.example.mobileproject.model.PotholeData;
import com.example.mobileproject.model.PotholeResponse;
import com.example.mobileproject.model.RegisterRequest;
import com.example.mobileproject.model.RegisterResponse;
import com.example.mobileproject.model.UpdatePasswordRequest;
import com.example.mobileproject.model.UpdatePasswordResponse;
import com.example.mobileproject.model.VerifyOtpRequest;
import com.example.mobileproject.model.VerifyOtpResponse;
import com.example.mobileproject.model.detectedPotholeRequest;
import com.example.mobileproject.model.detectedPotholeResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

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

    @POST("detected_pothole.php")
    @Headers("Content-Type: application/json")
    Call<detectedPotholeResponse> detectedPothole(@Body detectedPotholeRequest request);

    @POST("add_pothole.php")
    Call<ApiResponse> addPothole(@Body PotholeData data);

    @GET("get_pothole.php")
    @Headers("Content-Type: application/json")
    Call<List<PotholeResponse>> getPotholes();
    @GET("potholes/location")
    Call<PotholeResponse> getPotholeAtLocation(
            @Query("latitude") double latitude,
            @Query("longitude") double longitude
    );
}
