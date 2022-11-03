package com.example.mobileproject.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MonitorDao {
    @Query("SELECT * FROM Monitor WHERE case_id = :id")
    public List<Monitor> getMonitorsListByCaseId(Integer id);

    @Query("SELECT note FROM Monitor WHERE monitor_id = :id")
    public String getNoteById(Integer id);

    @Query("SELECT date FROM Monitor WHERE monitor_id = :id")
    public String getDateById(Integer id);

    @Insert
    public void insertMonitor(Monitor monitor);
}
