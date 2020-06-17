package com.awon.newsapp.http;

import com.awon.newsapp.model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("top-headlines")
    Call<ResponseModel> getNews(@Query("country") String country,
                                @Query("category") String category,
                                @Query("pageSize") int pageSize,
                                @Query("page") int page,
                                @Query("apiKey") String apiKey);
}
