package com.example.mobileproject.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    public int userId;

    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName;

    @NonNull
    @ColumnInfo(name = "last_name")
    public String lastName;

    @NonNull
    @ColumnInfo(name = "birth_date")
    public String birthDate;

    @NonNull
    @ColumnInfo(name = "gender")
    public String gender;

    @NonNull
    @ColumnInfo(name = "phone_number")
    public String phoneNumber;
}
