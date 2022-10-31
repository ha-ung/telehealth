package com.example.mobileproject.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = Patient.class,
                parentColumns = "patient_id",
                childColumns = "patient_id"
                //onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Doctor.class,
                parentColumns = "doctor_id",
                childColumns = "doctor_id"
                //onDelete = ForeignKey.CASCADE
        ),
        @ForeignKey(
                entity = Diagnosis.class,
                parentColumns = "diagnosis_id",
                childColumns = "diagnosis_id"
                //onDelete = ForeignKey.CASCADE
        ),
})
public class Cases {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "case_id")
    public Integer caseId;

    @NonNull
    @ColumnInfo(name = "patient_id")
    public Integer patientId;

    @NonNull
    @ColumnInfo(name = "doctor_id")
    public Integer doctorId;

    @ColumnInfo(name = "diagnosis_id")
    public Integer diagnosisId;

    @NonNull
    @ColumnInfo(name = "password")
    public String password;
}
