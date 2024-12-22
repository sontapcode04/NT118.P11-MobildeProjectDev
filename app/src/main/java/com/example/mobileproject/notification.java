package com.example.mobileproject;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class notification extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        RecyclerView recyclerView = findViewById(R.id.ListNoti);

        List<Item> items = new ArrayList<Item>();
        items.add(new Item(10.755368, 106.680485, "14:00:00 01/11/2024", 3));
        items.add(new Item(10.755368, 106.680485, "14:00:00 01/11/2024", 3));

        items.add(new Item(10.755368, 106.680485, "14:00:00 01/11/2024", 3));


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));

    }
}
