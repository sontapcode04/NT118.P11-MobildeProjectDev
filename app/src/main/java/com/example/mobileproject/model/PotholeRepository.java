package com.example.mobileproject.model;

import android.util.Log;
import android.widget.Toast;

import com.example.mobileproject.mapbox;
import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.model.PotholeData;
import com.example.mobileproject.model.PotholeResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PotholeRepository {
    private static final String TAG = "PotholeRepository";
    private final ApiService potholeApi;
    private static PotholeRepository instance;

    public interface PotholeCallback {
        void onSuccess(List<PotholeData> potholes);
        void onError(String message);
    }

    private PotholeRepository() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://nhom10.tanlamdevops.id.vn/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        potholeApi = retrofit.create(ApiService.class);
    }

    public static synchronized PotholeRepository getInstance() {
        if (instance == null) {
            instance = new PotholeRepository();
        }
        return instance;
    }

    public void getPotholes(PotholeCallback callback) {
        potholeApi.getPotholes().enqueue(new Callback<List<PotholeResponse>>() {
            @Override
            public void onResponse(Call<List<PotholeResponse>> call, Response<List<PotholeResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<PotholeData> potholes = new ArrayList<>();
                    for (PotholeResponse item : response.body()) {
                        Integer id = null;
                        try {
                            id = Integer.parseInt(item.getId());
                        } catch (NumberFormatException e) {
                            Log.e(TAG, "Error parsing ID: " + e.getMessage());
                        }

                        // Parse created_at string to Date
                        Date createdAt = null;
                        try {
                            if (item.getCreatedAt() != null) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                                createdAt = sdf.parse(item.getCreatedAt());
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Error parsing created_at: " + e.getMessage());
                        }

                        potholes.add(new PotholeData(
                                id,
                                item.getLatitude(),
                                item.getLongitude(),
                                item.getSeverity(),
                                item.getUserId(),
                                createdAt
                        ));
                    }
                    callback.onSuccess(potholes);
                } else {
                    callback.onError("Failed to get potholes");
                }
            }

            @Override
            public void onFailure(Call<List<PotholeResponse>> call, Throwable t) {
                Log.e(TAG, "Error getting potholes: " + t.getMessage());
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}