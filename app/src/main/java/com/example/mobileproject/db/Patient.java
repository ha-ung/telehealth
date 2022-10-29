package com.example.mobileproject.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(
                entity = User.class,
                parentColumns = "user_id",
                childColumns = "user_id"
                //onDelete = ForeignKey.CASCADE
        )
})
public class Patient {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "patient_id")
    public Integer patientId;

    @NonNull
    @ColumnInfo(name = "user_id")
    public Integer userId;
}
