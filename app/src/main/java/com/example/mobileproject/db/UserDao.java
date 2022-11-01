package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getAllUsers();

    @Query("SELECT (first_name || ' ' || last_name) as full_name FROM User WHERE user_id = :id")
    public String getFullNameById(Integer id);

    @Query("SELECT gender FROM User WHERE user_id = :id")
    public String getGenderById(Integer id);

    @Query("SELECT birth_date FROM User WHERE user_id = :id")
    public String getBirthDateById(Integer id);
}
