package com.example.blogapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    String BASE_URL = "http://api.mediastack.com/v1/";
    @GET("news?")
    Call<JSONPlaceHolder> getResult(@Query("access_key") String key, @Query("sources") String source);
}
