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
        ),
        @ForeignKey(
                entity = User.class,
                parentColumns = "user_id",
                childColumns = "user_id"
        )
})
public class Message {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "message_id")
    public Integer messageId;

    @NonNull
    @ColumnInfo(name = "case_id")
    public Integer caseId;

    @NonNull
    @ColumnInfo(name = "user_id")
    public Integer userId;

    @NonNull
    @ColumnInfo(name = "user_name")
    public String userName;

    @NonNull
    @ColumnInfo(name = "text")
    public String text;

    @NonNull
    @ColumnInfo(name = "date")
    public String date;
}
