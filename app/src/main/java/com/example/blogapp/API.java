package com.example.blogapp;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/*public interface API {

    String BASE_URL = "https://newsapi.org/v2/";
    @GET("everything?")
    Call<JSONPlaceHolder> getResult(@Query("q") String q, @Query("apiKey") String key);
}*/

public interface API {

    String BASE_URL = "https://api.weatherapi.com/v1/";
    @GET("current.json?")

    Call<JSONPlaceHolder> getWeather(@Query("key") String key, @Query("q") String loc);
}
