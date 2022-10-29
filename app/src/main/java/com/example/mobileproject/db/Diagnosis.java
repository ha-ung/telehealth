package com.example.mobileproject.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diagnosis {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "diagnosis_id")
    public Integer diagnosisId;

    @NonNull
    @ColumnInfo(name = "name")
    public String name;
}
