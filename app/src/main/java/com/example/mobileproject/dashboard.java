package com.example.mobileproject;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Color;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;

public class dashboard extends AppCompatActivity {
    PieChart pieChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        ImageView setting = findViewById(R.id.imageViewSetting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(dashboard.this, setting.class);
                startActivity(intent);
            }
        });

        // vẽ chart
        pieChart = findViewById(R.id.Chart);
        setupPieChart();
        loadPieChartData();
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

    private void loadPieChartData() {
        // Dữ liệu cho các mức độ ổ gà
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(25f, "Low"));
        entries.add(new PieEntry(35f, "Medium"));
        entries.add(new PieEntry(40f, "High"));

        // Cấu hình màu sắc
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#4CAF50")); // Green (Low)
        colors.add(Color.parseColor("#FF9800")); // Orange (Medium)
        colors.add(Color.parseColor("#F44336")); // Red (High)

        // Tạo PieDataSet và gán vào PieChart
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);
        dataSet.setSliceSpace(3f); // Khoảng cách giữa các lát
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);

        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // Refresh để hiển thị dữ liệu
    }
}
