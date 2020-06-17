package com.awon.newsapp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.awon.newsapp.database.Entry;
import com.awon.newsapp.model.Article;
import com.awon.newsapp.model.viewmodel.ViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Entry> convertToDb(List<Article> articles) {
        List<Entry> entries = new ArrayList<>();
        for (Article article :
                articles) {
            Entry entry = new Entry(article.getSource().getId(), article.getSource().getName(), article.getAuthor(), article.getTitle(),
                    article.getDescription(), article.getUrl(), article.getUrlToImage(), article.getPublishedAt(), article.getContent());
            entries.add(entry);
        }
        return entries;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    public static void showSnackbar(View v, String message, Integer length) {
        if (length == Snackbar.LENGTH_SHORT){
            Snackbar.make(v,message,Snackbar.LENGTH_SHORT).show();
        } else if (length == Snackbar.LENGTH_LONG){
            Snackbar.make(v,message,Snackbar.LENGTH_LONG).show();
        }
    }
}
