package com.example.mobileproject.model;

import android.util.Log;
import android.widget.Toast;

import com.example.mobileproject.mapbox;
import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.model.PotholeData;
import com.example.mobileproject.model.PotholeResponse;
import java.util.ArrayList;
import java.util.List;
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

    public interface AddPotholeCallback {
        void onSuccess();
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

                        potholes.add(new PotholeData(
                                id,
                                item.getLatitude(),
                                item.getLongitude(),
                                item.getSeverity(),
                                0
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
                Log.e(TAG, "Stack trace: ", t);
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }

    public void addPothole(PotholeData pothole, AddPotholeCallback callback) {
        potholeApi.addPothole(pothole).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onError("Failed to add pothole");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callback.onError("Network error: " + t.getMessage());
            }
        });
    }
}