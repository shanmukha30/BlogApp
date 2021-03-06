package com.example.blogapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    @BindView(R.id.newsRecyclerView) RecyclerView newsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);

        NewsRecyclerViewAdapter newsAdapter = new NewsRecyclerViewAdapter(this, searchList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        newsRecyclerView.setLayoutManager(layoutManager);
        newsRecyclerView.setAdapter(newsAdapter);

        goButton.setOnClickListener(v -> {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (NewsActivity.this.getCurrentFocus() != null){
                inputMethodManager.hideSoftInputFromWindow(NewsActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
            if (!searchEditText.getText().toString().isEmpty()) {
                ProgressDialog progress = new ProgressDialog(NewsActivity.this);
                progress.setMessage("Searching...");
                progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(API.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                searchList.clear();
                API myApi = retrofit.create(API.class);
                try {
                    String urlEncoder = URLEncoder.encode(searchEditText.getText().toString(), "UTF-8");
                    Call<JSONPlaceHolder> call = myApi.getResult(urlEncoder, "b09bf3b0daaa4ea88fa79218bff2c973");
                    call.enqueue(new Callback<JSONPlaceHolder>() {
                        @Override
                        public void onResponse(@NonNull Call<JSONPlaceHolder> call, @NonNull Response<JSONPlaceHolder> response) {
                            if (response.isSuccessful() && response.body().getArticles() != null) {
                                ArrayList<Article> searchResults = response.body().getArticles();
                                int n;
                                if (searchResults.size() > 15) {
                                    n = 15;
                                } else {
                                    n = searchResults.size();
                                }
                                for (int i = 0; i < n; i++) {
                                    Map<String, String> entry = new HashMap<>();
                                    if (searchResults.get(i).getTitle() != null && searchResults.get(i).getUrl() != null) {
                                        entry.put("title", searchResults.get(i).getTitle());
                                        if (searchResults.get(i).getSource().getName() != null) {
                                            entry.put("name", searchResults.get(i).getSource().getName());
                                        }else{
                                            entry.put("name", "Source not available");
                                        }
                                        if (searchResults.get(i).getDescription() != null) {
                                            entry.put("description", searchResults.get(i).getDescription());
                                        }else{
                                            entry.put("description", "No content description");
                                        }
                                        entry.put("imgurl", searchResults.get(i).getUrlToImage());
                                        entry.put("url", searchResults.get(i).getUrl());
                                        searchList.add(entry);
                                    }
                                }
                                Log.i("info", searchList.toString());
                                newsAdapter.notifyDataSetChanged();
                            } else {
                                Toast.makeText(NewsActivity.this, "No results", Toast.LENGTH_LONG).show();
                            }
                            progress.dismiss();
                        }

                        @Override
                        public void onFailure(@NonNull Call<JSONPlaceHolder> call, @NonNull Throwable t) {
                            Toast.makeText(NewsActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Log.e("Error", t.getMessage());
                            progress.dismiss();
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Toast.makeText(NewsActivity.this, "Unknown error occurred", Toast.LENGTH_LONG).show();
                    progress.dismiss();
                }
            }else{
                Toast.makeText(this, "Field cannot be empty", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewsActivity.this, MainActivity.class));
        finish();
    }
}