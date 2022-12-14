package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DoctorDao {

    @Query("SELECT * FROM Doctor WHERE doctor_id = :id")
    public Doctor getDoctorById(Integer id);

    @Query("SELECT password FROM Doctor WHERE doctor_id = :id")
    public String getPasswordById(Integer id);

    @Query("SELECT user_id FROM Doctor WHERE doctor_id = :id")
    public Integer getUserIdById(Integer id);

    @Query("UPDATE Doctor SET password = :password WHERE doctor_id = :id")
    public void updatePasswordById(Integer id, String password);

}
