package com.example.mobileproject;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.mobileproject.model.PotholeData;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class PotholeDetailDialog extends Dialog {
    private TextView tvLocation, tvSeverity, tvLongitude, tvLatitude, tvReportTime;
    private Button btnBack;

    public PotholeDetailDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mapview_show_detail_report_pothole);

        // Initialize views
        tvLocation = findViewById(R.id.tv_location);
        tvSeverity = findViewById(R.id.tv_severity);
        tvLongitude = findViewById(R.id.tv_longtitudex);
        tvLatitude = findViewById(R.id.tv_latitudeY);
        tvReportTime = findViewById(R.id.tv_report_time);
        btnBack = findViewById(R.id.btn_back_pothole_detail_map);

        btnBack.setOnClickListener(v -> dismiss());
    }

    public void showPotholeDetails(PotholeData pothole) {

        tvSeverity.setText(pothole.getSeverity());
        tvLongitude.setText(String.format(Locale.getDefault(), "%.6f", pothole.getLongitude()));
        tvLatitude.setText(String.format(Locale.getDefault(), "%.6f", pothole.getLatitude()));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy", Locale.getDefault());


        show();
    }
}