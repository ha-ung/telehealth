package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.Monitor;
import com.example.mobileproject.db.MonitorDao;
import com.example.mobileproject.db.PatientDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;

import java.util.List;

public class CaseDetailActivity extends AppCompatActivity {
    private List<Monitor> monitorsList;
    private RecyclerView recyclerView;
    private MonitorsListAdapter adapter;

    private Integer patientId;
    private TextView patientName;
    private Integer caseId;
    private TextView caseTitle;
    private Integer userId;
    private String fullName;
    private String gender;
    private String birthDate;
    private TextView patientGender;
    private TextView patientBirthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);

        caseTitle = findViewById(R.id.case_title);
        patientName = findViewById(R.id.patient_name);
        patientGender = findViewById(R.id.patient_gender);
        patientBirthDate = findViewById(R.id.patient_birth_date);

        Intent intent = getIntent();
        caseId = intent.getIntExtra(CasesListAdapter.EXTRA_ID, 0);
        caseTitle.setText("Case #" + String.valueOf(caseId));

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getApplicationContext());
        MonitorDao monitorDao = appDatabase.monitorDao();
        CasesDao casesDao = appDatabase.casesDao();
        PatientDao patientDao = appDatabase.patientDao();
        UserDao userDao = appDatabase.userDao();

        monitorsList = monitorDao.getMonitorsListByCaseId(caseId);
        patientId = casesDao.getPatientIdById(caseId);
        userId = patientDao.getUserIdById(patientId);
        fullName = userDao.getFullNameById(userId);
        gender = userDao.getGenderById(userId);
        birthDate = userDao.getBirthDateById(userId);

        patientName.setText("Patient: " + fullName);
        patientGender.setText("Gender: " + gender);
        patientBirthDate.setText("Birth date: " + birthDate);

        recyclerView = findViewById(R.id.monitors_list);
        adapter = new MonitorsListAdapter(this, monitorsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}