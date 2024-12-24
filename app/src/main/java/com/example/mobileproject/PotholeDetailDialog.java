package com.example.mobileproject;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobileproject.model.PotholeData;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Date;

public class PotholeDetailDialog extends Dialog {
    private TextView tvLocation, tvSeverity, tvLongitude, tvLatitude, tvReportTime;
    private Button btnBack;
    private PotholeData pothole;

    public PotholeDetailDialog(Context context, PotholeData pothole) {
        super(context);
        this.pothole = pothole;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapview_show_detail_report_pothole);

        // Initialize views
        tvLocation = findViewById(R.id.tv_location);
        tvSeverity = findViewById(R.id.tv_severity);
        tvLongitude = findViewById(R.id.tv_longtitudex);
        tvLatitude = findViewById(R.id.tv_latitudeY);
        tvReportTime = findViewById(R.id.tv_report_time);
        btnBack = findViewById(R.id.btn_back_pothole_detail_map);

        showPotholeDetails();
        btnBack.setOnClickListener(v -> dismiss());
    }

    private void showPotholeDetails() {
        tvLocation.setText(String.format("Location: %.6f, %.6f",
                pothole.getLatitude(), pothole.getLongitude()));

//        // Chuyển đổi severity từ số sang mô tả
//        String severityDesc;
//        try {
//            int severityLevel = Integer.parseInt(pothole.getSeverity());
//            switch (severityLevel) {
//                case 1:
//                    severityDesc = "Low";
//                    break;
//                case 2:
//                    severityDesc = "Medium";
//                    break;
//                case 3:
//                    severityDesc = "High";
//                    break;
//                default:
//                    severityDesc = "Low";
//            }
//        } catch (NumberFormatException e) {
//            severityDesc = "Unknown";
//        }
        tvSeverity.setText("Severity: " + "Medium");

        tvLongitude.setText(String.format("%.6f", pothole.getLongitude()));
        tvLatitude.setText(String.format("%.6f", pothole.getLatitude()));

        Date createdAt = pothole.getCreatedAt();
        if (createdAt != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
            tvReportTime.setText(sdf.format(createdAt));
        } else {
            tvReportTime.setText("Not available");
        }
    }
}