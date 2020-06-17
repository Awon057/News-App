package com.awon.newsapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefClient {
    public static SharedPreferences getPreferenceName(Context context){
        return context.getSharedPreferences(context.getPackageName()+"_preferences", Context.MODE_PRIVATE);
    }

    public static void setInt(Context context, String key, int value){
        SharedPreferences pref = getPreferenceName(context);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(key, value).commit();
    }

    public static int getInt(Context context, String key){
        SharedPreferences pref = getPreferenceName(context);
        return pref.contains(key)?pref.getInt(key, 0):0;
    }

    public static void remove(Context context, String key){
        SharedPreferences pref = getPreferenceName(context);
        pref.edit().remove(key).commit();
    }
}
