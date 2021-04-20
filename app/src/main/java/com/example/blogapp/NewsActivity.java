package com.example.blogapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NewsActivity extends AppCompatActivity {

    static ArrayList<Map<String, String>> searchList = new ArrayList<>();
    @BindView(R.id.goButton) Button goButton;
    @BindView(R.id.searchEditText) EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        NewsRecyclerViewAdapter newsAdapter = new NewsRecyclerViewAdapter(this, searchList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);
        ButterKnife.bind(this);

        goButton.setOnClickListener(v -> {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            searchList.clear();
            API myApi = retrofit.create(API.class);
            try {
                String urlEncoder = URLEncoder.encode(searchEditText.getText().toString(), "UTF-8");
                Call<JSONPlaceHolder> call = myApi.getWeather("ee1e6fc97eae412a8f3125336211202", urlEncoder);
                call.enqueue(new Callback<JSONPlaceHolder>() {
                    @Override
                    public void onResponse(@NonNull Call<JSONPlaceHolder> call, @NonNull Response<JSONPlaceHolder> response) {
                        Log.i("infoxx", response.body().getCurrent().getCondition().getText());
                        /*ArrayList<Article> searchResults = response.body().getArticles();
                        for (int i = 0; i < searchResults.size(); i++) {
                            Map<String, String> entry = new HashMap<>();
                            entry.put("title", searchResults.get(i).getTitle());
                            entry.put("source", searchResults.get(i).getSource().getName());
                            entry.put("imgurl", searchResults.get(i).getUrlToImage());
                            entry.put("url", searchResults.get(i).getUrl());
                            searchList.add(entry);
                        }
                        newsAdapter.notifyDataSetChanged();*/
                    }

                    @Override
                    public void onFailure(@NonNull Call<JSONPlaceHolder> call, @NonNull Throwable t) {
                        Toast.makeText(NewsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("Error", t.getMessage());
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        });
    }
}