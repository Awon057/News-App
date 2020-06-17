package com.awon.newsapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Entry> users);

    @Query("Select * from Entry order by publishedAt desc")
    List<Entry> getAll();
}
