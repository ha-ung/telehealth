package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CasesDao {
    @Query("SELECT * FROM Cases WHERE case_id = :id")
    public Cases getCasesById(Integer id);
}
