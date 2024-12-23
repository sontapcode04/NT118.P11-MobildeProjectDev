package com.example.mobileproject;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Color;
import android.widget.TextView;
import android.content.SharedPreferences;

import com.example.mobileproject.model.PotholeData;
import com.example.mobileproject.model.PotholeRepository;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        AnhXa();
        ImageView setting = findViewById(R.id.imageViewSetting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, setting.class);
                startActivity(intent);
            }
        });

        ImageView mapbox = findViewById(R.id.imageViewMap);
        mapbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, mapbox.class);
                startActivity(intent);
            }
        });

        ImageView noti = findViewById(R.id.notificationIcon);
        noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, notification.class);
                startActivity(intent);
            }
        });

        // vẽ chart
        pieChart = findViewById(R.id.Chart);
        setupPieChart();
//        loadPieChartData();
        loadPotholesFromServerToDashboard();
    }

    private void setupPieChart() {
        // Cấu hình chung cho PieChart
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText("Potholes");
        pieChart.setCenterTextSize(14);
        pieChart.getDescription().setEnabled(false);

        // loại bỏ chú thích
        Legend legend = pieChart.getLegend();
        legend.setEnabled(false);
    }

    private void loadPotholesFromServerToDashboard() {
        Log.d("Dashboard", "Starting to load potholes from server");

        // Lấy user_id của người đang đăng nhập từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int currentUserId = sharedPreferences.getInt("user_id", -1);

        // Log để debug
        Log.d("Dashboard", "Current user ID: " + currentUserId);

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

                // Đếm số lượng pothole theo mức độ của user đang đăng nhập
                int lowCount = 0;
                int mediumCount = 0;
                int highCount = 0;

                for (PotholeData pothole : userPotholes) {
                    int severity = (int) pothole.getSeverity();
                    switch (severity) {
                        case 1:
                            lowCount++;
                            break;
                        case 2:
                            mediumCount++;
                            break;
                        case 3:
                            highCount++;
                            break;
                        default:
                            Log.w("Dashboard", "Unknown severity: " + severity);
                    }
                }

                // Log để debug
                Log.d("Dashboard", "User's potholes count - Low: " + lowCount 
                                                        + ", Medium: " + mediumCount 
                                                        + ", High: " + highCount);

                // Cập nhật biểu đồ
                updatePieChart(lowCount, mediumCount, highCount);
            }

            @Override
            public void onError(String message) {
                Log.e("Dashboard", "Error loading potholes: " + message);
            }
        });
    }

    private void updatePieChart(int lowCount, int mediumCount, int highCount) {
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(lowCount, "Low"));
        entries.add(new PieEntry(mediumCount, "Medium"));
        entries.add(new PieEntry(highCount, "High"));

        // Cấu hình màu sắc
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4CAF50")); // Green (Low)
        colors.add(Color.parseColor("#FF9800")); // Orange (Medium)
        colors.add(Color.parseColor("#F44336")); // Red (High)

        // Tạo PieDataSet và gán vào PieChart
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(3f);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh biểu đồ
    }
//    private void loadPieChartData() {
//        // Dữ liệu cho các mức độ ổ gà
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(25f, "Low"));
//        entries.add(new PieEntry(35f, "Medium"));
//        entries.add(new PieEntry(40f, "High"));
//
//        // Cấu hình màu sắc
//        ArrayList<Integer> colors = new ArrayList<>();
//        colors.add(Color.parseColor("#4CAF50")); // Green (Low)
//        colors.add(Color.parseColor("#FF9800")); // Orange (Medium)
//        colors.add(Color.parseColor("#F44336")); // Red (High)
//
//        // Tạo PieDataSet và gán vào PieChart
//        PieDataSet dataSet = new PieDataSet(entries, "");
//        dataSet.setColors(colors);
//        dataSet.setSliceSpace(3f); // Khoảng cách giữa các lát
//        dataSet.setValueTextColor(Color.WHITE);
//        dataSet.setValueTextSize(12f);
//
//        PieData data = new PieData(dataSet);
//        pieChart.setData(data);
//        pieChart.invalidate(); // Refresh để hiển thị dữ liệu
//    }

    private void AnhXa() {
        TextView HelloUser = findViewById(R.id.helloUser);
        TextView NameUser = findViewById(R.id.NameUser);
        TextView ScoreUser = findViewById(R.id.ScoreUser);
        ImageView notification = findViewById(R.id.notificationIcon);

        // Lấy thông tin user từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String fullName = sharedPreferences.getString("full_name", "User");
        String email = sharedPreferences.getString("email", "");

        // Hiển thị thông tin
        HelloUser.setText("Hi, " + fullName);
        NameUser.setText(email);
        // ScoreUser.setText("Score: " + score); // Nếu bạn có score
    }
}
