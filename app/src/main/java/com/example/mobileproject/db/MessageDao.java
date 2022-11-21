package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MessageDao {
    @Query("SELECT * FROM Message WHERE case_id = :id")
    public List<Message> getMessageByCaseId(Integer id);

    @Insert
    public void insertMessage(Message message);
}
