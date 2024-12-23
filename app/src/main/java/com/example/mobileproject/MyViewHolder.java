package com.example.mobileproject;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder  {
    double latitude, longitude;
    int severity;
    String createAt;
    TextView latitudetv, longitudetv, severitytv, createAttv;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        latitudetv = itemView.findViewById(R.id.latitude);
        longitudetv = itemView.findViewById(R.id.longitude);
        severitytv = itemView.findViewById(R.id.severity);
        createAttv = itemView.findViewById(R.id.createAt);

        try {
            latitude = Double.parseDouble(latitudetv.getText().toString());
            longitude = Double.parseDouble(longitudetv.getText().toString());
            severity = Integer.parseInt(severitytv.getText().toString());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Gán giá trị mặc định nếu xảy ra lỗi
            latitude = 0.0;
            longitude = 0.0;
            severity = 0;
        }
        createAt = createAttv.getText().toString();
    }
}
