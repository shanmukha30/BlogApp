package com.example.blogapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    String BASE_URL = "https://newsapi.org/v2/";
    @GET("everything?")
    Call<JSONPlaceHolder> getResult(@Query("q") String q, @Query("apiKey") String key);
}
