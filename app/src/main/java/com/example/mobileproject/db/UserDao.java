package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

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

    @Query("UPDATE User SET first_name = :firstname , last_name = :lastname, birth_date = :birthday, phone_number = :phone " +
            "WHERE user_id =:id")
    public void updateProfile(Integer id, String firstname, String lastname, String birthday, String phone);
    @Query("UPDATE Cases SET password = :password WHERE patient_id = :patient_id")
    public void updatePassword(Integer patient_id, String password);

}
