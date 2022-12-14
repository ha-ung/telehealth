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

    @Query("SELECT * FROM Cases WHERE patient_id = :id")
    public List<Cases> getAllCasesByPatientId(Integer id);

    @Query("SELECT patient_id FROM Cases WHERE case_id = :id")
    public Integer getPatientIdById(Integer id);

    @Query("SELECT doctor_id FROM Cases WHERE case_id = :id")
    public Integer getDoctorIdById(Integer id);

    @Query("SELECT phone_number FROM User WHERE user_id IN (SELECT user_id FROM Doctor WHERE doctor_id IN (SELECT doctor_id FROM Cases WHERE case_id = :id))")
    public String getDoctorPhoneNumberByCaseId(Integer id);

    @Query("UPDATE Cases SET password = :password WHERE case_id = :id")
    public void updatePasswordById(Integer id, String password);
}
