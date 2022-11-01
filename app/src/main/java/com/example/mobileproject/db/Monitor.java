package com.example.mobileproject.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
    @ForeignKey(
        entity = Cases.class,
        parentColumns = "case_id",
        childColumns = "case_id"
    )
})
public class Monitor {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "monitor_id")
    public Integer monitorId;

    @NonNull
    @ColumnInfo(name = "case_id")
    public Integer caseId;

    @NonNull
    @ColumnInfo(name = "symptom")
    public String symptom;

    @ColumnInfo(name = "note")
    public String note;

    @NonNull
    @ColumnInfo(name = "date")
    public String date;
}
