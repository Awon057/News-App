package com.awon.newsapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Entry.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static final Object LOCK = new Object();

    public abstract NewsDao userDao();

    public static AppDatabase getInstance(Context context){
        if (instance==null){
            synchronized(LOCK) {
                instance = Room.databaseBuilder(context, AppDatabase.class, "Demo.db").build();
            }
        }
        return instance;
    }
}
