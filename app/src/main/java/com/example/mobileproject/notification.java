package com.example.mobileproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileproject.model.PotholeData;
import com.example.mobileproject.model.PotholeRepository;

import java.util.ArrayList;
import java.util.List;

public class notification extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        loadPotholesFromServerToDashboard();
        //back button
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        RecyclerView recyclerView = findViewById(R.id.ListNoti);

//        List<Item> items = new ArrayList<Item>();
//        items.add(new Item(10.755368, 106.680485, "2024-12-10 20:48:45", 3));
//        items.add(new Item(10.8840129, 106.7797819, "2024-12-10 20:48:41", 1));
//        items.add(new Item(10.88386, 106.779659, "2024-12-10 20:48:47", 3));
//
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));

    }

    private void loadPotholesFromServerToDashboard() {
        Log.d("notification", "Starting to load potholes from server");

        // Lấy user_id của người đang đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int currentUserId = sharedPreferences.getInt("user_id", 17);

        // Log để debug
        Log.d("notification", "Current user ID: " + currentUserId);

        PotholeRepository.getInstance().getPotholes(new PotholeRepository.PotholeCallback() {
            @Override
            public void onSuccess(List<PotholeData> potholes) {
                Log.d("Dashboard", "Successfully loaded " + potholes.size() + " potholes");


                // Lọc potholes của user đang đăng nhập
                List<PotholeData> userPotholes = new ArrayList<>();
                for (PotholeData pothole : potholes) {
                    if (pothole.getUserId() == currentUserId) {
                        userPotholes.add(pothole);
                    }
                }

                // Lấy 5 item đầu tiên (hoặc ít hơn nếu danh sách có ít hơn 5 item)
                List<Item> itemsToDisplay = new ArrayList<>();
                int limit = Math.min(userPotholes.size(), 5);
                for (int i = 0; i < limit; i++) {
                    PotholeData pothole = userPotholes.get(i);
                    String createdAt = (pothole.getCreatedAt() != null) ? pothole.getCreatedAt().toString() : "N/A"; // Kiểm tra null
                    itemsToDisplay.add(new Item(
                            pothole.getLatitude(),
                            pothole.getLongitude(),
                            pothole.getFormattedCreatedAt(),
                            Integer.parseInt(pothole.getSeverity())
                    ));
                }

                // Cập nhật RecyclerView
                RecyclerView recyclerView = findViewById(R.id.ListNoti);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(new MyAdapter(getApplicationContext(), itemsToDisplay));

            }

            @Override
            public void onError(String message) {
                Log.e("notification", "Error loading potholes: " + message);
            }
        });
    }
}