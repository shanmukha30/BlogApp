package com.example.blogapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    static ArrayList<String> newsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RecyclerView NewsRecyclerView = findViewById(R.id.NewsRecyclerView);

        NewsRecyclerViewAdapter newsAdapter = new NewsRecyclerViewAdapter(this, newsList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        NewsRecyclerView.setLayoutManager(layoutManager);
        NewsRecyclerView.setAdapter(newsAdapter);
    }
}