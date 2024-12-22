package com.example.mobileproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    Context context;
    List<Item> items;
    public MyAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.latitudetv.setText(String.valueOf(items.get(position).getLatitude()));
        holder.longitudetv.setText(String.valueOf(items.get(position).getLongitude()));
        holder.severitytv.setText(String.valueOf(items.get(position).getSeverity()));
        holder.createAttv.setText(String.valueOf(items.get(position).getCreateAt()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
