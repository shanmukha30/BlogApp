package com.example.blogapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    ArrayList<Map<String, String>> searchList = new ArrayList<>();
    static ArrayList<String> favouritesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView FavouritesRecyclerView = findViewById(R.id.FavouritesRecyclerView);

        FavouritesRecyclerViewAdapter favouritesAdapter = new FavouritesRecyclerViewAdapter(this, favouritesList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        FavouritesRecyclerView.setLayoutManager(layoutManager);
        FavouritesRecyclerView.setAdapter(favouritesAdapter);

        ButterKnife.bind(this);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        }

        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.logoutButton){
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            }
            else if(item.getItemId() == R.id.searchButton){
                Intent intent = new Intent(this,NewsActivity.class);
                startActivity(intent);
            }
            return false;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        API myApi = retrofit.create(API.class);
        Call<ArrayList<JSONPlaceHolder>> call = myApi.getResult("b09bf3b0daaa4ea88fa79218bff2c973", "tesla");
        call.enqueue(new Callback<ArrayList<JSONPlaceHolder>>() {
            @Override
            public void onResponse(Call<ArrayList<JSONPlaceHolder>> call, Response<ArrayList<JSONPlaceHolder>> response) {
                ArrayList<JSONPlaceHolder> searchResults = response.body();
                for (int i = 0; i < searchResults.size(); i++) {
                    Map<String, String> entry = new HashMap<>();
                    entry.put("title", searchResults.get(i).getTitle());
                    entry.put("source", searchResults.get(i).getSource().getName());
                    entry.put("imgurl", searchResults.get(i).getUrlToImage());
                    entry.put("url", searchResults.get(i).getUrl());
                    searchList.add(entry);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<JSONPlaceHolder>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("Error", t.getMessage());
            }
        });
    }
}