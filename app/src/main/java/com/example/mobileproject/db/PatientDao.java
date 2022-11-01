package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT user_id FROM patient WHERE patient_id = :id")
    public Integer getUserIdById(Integer id);
}
