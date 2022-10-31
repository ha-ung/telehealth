package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CasesDao {
    @Query("SELECT * FROM Cases WHERE case_id = :id")
    public Cases getCasesById(Integer id);

    @Query("SELECT password FROM Cases WHERE case_id = :id")
    public String getPasswordById(Integer id);

    @Query("SELECT * FROM Cases WHERE doctor_id = :id")
    public List<Cases> getAllCasesByDoctorId(Integer id);
}
