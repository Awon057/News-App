package com.awon.newsapp.sync;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.awon.newsapp.http.ApiClient;
import com.awon.newsapp.http.ApiInterface;
import com.awon.newsapp.model.ResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsSync {
    private static ApiInterface api;

    public static LiveData<ResponseModel> getAll(int page) {
        final MutableLiveData<ResponseModel> data = new MutableLiveData<>();
        try {
            api = ApiClient.getGetService();
            Call<ResponseModel> call = api.getNews("us", "business", 20, page, "fcd83257cebb4bb0b7a87cc9afdf0c8b");
            call.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.isSuccessful()) {
                        data.postValue(response.body());
                    } else {
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
