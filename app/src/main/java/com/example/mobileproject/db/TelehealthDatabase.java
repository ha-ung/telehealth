package com.example.mobileproject.db;
import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.io.File;

@Database(entities = {User.class, Patient.class, Doctor.class, Diagnosis.class, Cases.class}, version = 1)
public abstract class TelehealthDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract DiagnosisDao diagnosisDao();
    public abstract DoctorDao doctorDao();
    public abstract PatientDao patientDao();
    public abstract CasesDao casesDao();
    private static TelehealthDatabase INSTANCE;

    public static TelehealthDatabase getDbInstance(Context context)  {
        File databasesDir = new File(context.getApplicationInfo().dataDir + "/databases");
        new File(databasesDir, "telehealth.db").delete();
        context.deleteDatabase("telehealth.db");
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), TelehealthDatabase.class, "telehealth_db")
                    .allowMainThreadQueries()
                    .createFromAsset("databases/telehealth.db")
                    .build();
        }
        return INSTANCE;
    }
}
