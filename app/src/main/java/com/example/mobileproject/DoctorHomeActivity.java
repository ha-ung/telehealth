package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.mobileproject.db.Cases;
import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.TelehealthDatabase;

import java.util.ArrayList;
import java.util.List;

public class DoctorHomeActivity extends AppCompatActivity {

    private List<Cases> casesList;
    private RecyclerView recyclerView;
    private CasesListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        Intent intent = getIntent();
        int id = intent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getApplicationContext());
        CasesDao casesDao = appDatabase.casesDao();
        casesList = casesDao.getAllCasesByDoctorId(id);

        recyclerView = findViewById(R.id.cases_list);
        adapter = new CasesListAdapter(this, casesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d("doctor's id", String.valueOf(id));
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, PatientHomeActivity.class);
        startActivity(intent);
    }
}