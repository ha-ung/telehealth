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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertUser(User...users);

    @Delete
    public void deleteUser(User user);
}
