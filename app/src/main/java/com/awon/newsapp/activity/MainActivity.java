package com.awon.newsapp.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.awon.newsapp.database.Entry;
import com.awon.newsapp.adapter.NewsListAdapter;
import com.awon.newsapp.databinding.ActivityMainBinding;
import com.awon.newsapp.util.PassModel;
import com.awon.newsapp.R;
import com.awon.newsapp.model.ResponseModel;
import com.awon.newsapp.model.viewmodel.ViewModel;
import com.awon.newsapp.database.AppDatabase;
import com.awon.newsapp.util.PreferenceKey;
import com.awon.newsapp.util.SharedPrefClient;
import com.awon.newsapp.util.Utils;
import com.facebook.stetho.Stetho;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
//import com.awon.newsapp.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private NewsListAdapter adapter;
    private ViewModel viewModel;
    private AppDatabase instance;
    private ActivityMainBinding binding;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        Stetho.initializeWithDefaults(this);

        layoutManager = new LinearLayoutManager(this);
        binding.newslistRecyclerView.setLayoutManager(layoutManager);
        adapter = new NewsListAdapter();
        viewModel = ViewModelProviders.of(this).get(ViewModel.class);
        checkInternet();
        viewModel.setNews(page);
        binding.newslistRecyclerView.setAdapter(adapter);

        binding.swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                checkInternet();
                if (page * 20 < SharedPrefClient.getInt(MainActivity.this, PreferenceKey.totalNews)) {
                    page += 1;
                    viewModel.setNews(page);
                    observeFunc();
                }
                binding.swipeRefresh.setRefreshing(false);
            }
        });

        observeFunc();
        getData();
    }

    private void checkInternet() {
        if (!Utils.isConnectedToInternet(MainActivity.this))
            Utils.showSnackbar(getWindow().getDecorView().getRootView(), "Please Check your internet Connection", Snackbar.LENGTH_SHORT);
    }

    private void observeFunc() {
        instance = AppDatabase.getInstance(MainActivity.this);
        viewModel.getNews().observe(this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(@Nullable final ResponseModel newsList) {
                final ResponseModel data = newsList;
                final List<Entry> entries = Utils.convertToDb(data.getArticles());
                SharedPrefClient.setInt(MainActivity.this, PreferenceKey.totalNews, newsList.getTotalResults());
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            instance.userDao().insertAll(entries);
                        } finally {
                            getData();
                        }
                    }
                };
                new Thread(runnable).start();
            }
        });
    }

    private void getData() {
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Entry> entries = instance.userDao().getAll();
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.setnews(entries, MainActivity.this, passModel);
                    }
                });
            }
        };
        new Thread(runnable).start();
    }

    PassModel passModel = new PassModel() {
        @Override
        public void passData(Entry entry) {
            if (Utils.isConnectedToInternet(MainActivity.this)) {
                Intent i = new Intent(MainActivity.this, NewsDetailsActivity.class);
                i.putExtra("article", entry);
                startActivity(i);
            } else {
                Utils.showSnackbar(getWindow().getDecorView().getRootView(), "Please Check your internet Connection", Snackbar.LENGTH_SHORT);
            }
        }
    };
}
