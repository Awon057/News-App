package com.awon.newsapp.model.viewmodel;

import androidx.lifecycle.LiveData;

import com.awon.newsapp.sync.NewsSync;
import com.awon.newsapp.model.ResponseModel;

public class ViewModel extends androidx.lifecycle.ViewModel {
    public LiveData<ResponseModel> news;

    public void setNews(int page) {
        news = NewsSync.getAll(page);
    }

    public LiveData<ResponseModel> getNews() {
        return news;
    }
}
