package com.example.mobileproject.api;


import com.example.mobileproject.model.LoginRequest;
import com.example.mobileproject.model.LoginResponse;
import com.example.mobileproject.model.RegisterRequest;
import com.example.mobileproject.model.RegisterResponse;

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
}
