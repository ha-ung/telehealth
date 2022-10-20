package com.example.mobileproject.db;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    private static AppDatabase INSTANCE;



    public static AppDatabase getDbInstance(Context context)  {
        if (INSTANCE == null) {
            INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "telehealth_db")
                            .allowMainThreadQueries()
                            .createFromAsset("/db/telehealth.db")
                            .build();
        }
        return INSTANCE;
    }
}
