package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mobileproject.db.Cases;
import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.DoctorDao;
import com.example.mobileproject.db.Monitor;
import com.example.mobileproject.db.MonitorDao;
import com.example.mobileproject.db.Patient;
import com.example.mobileproject.db.PatientDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PatientHomeActivity extends AppCompatActivity {
    //public FloatingActionButton fab;
    public static final String EXTRA_ID = "com.example.android.mobileproject.extra.ID";
    public static final int TEXT_REQUEST = 1;
    public FloatingActionButton addList;
    public BottomNavigationView bottomNav;
    public LinearLayout linear;
    public String TELEPHONE="0123456789";

    private static RecyclerView recyclerView;
    private static MonitorsListAdapter adapter;

    private List<Monitor> monitorsList;
    private Integer doctorId;
    private Integer userId;
    private String fullName;
    private Integer caseId;
    private TextView doctorName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        doctorName = findViewById(R.id.doctor_name);

        Intent intentId = getIntent();
        caseId = intentId.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);

        //fab = findViewById(R.id.callButton);
        addList = findViewById(R.id.add_list_button);
        bottomNav = findViewById(R.id.bottomNavigationView);

        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addList = new Intent(getApplicationContext(), AddListActivity.class);
                addList.putExtra(EXTRA_ID, caseId);
                startActivityForResult(addList, TEXT_REQUEST);
            }
        });

//        fab.setOnClickListener(v -> { //View v
//            Intent intent = new Intent(Intent.ACTION_DIAL);
//            String uri = "tel:" + TELEPHONE;
//            intent.setData(Uri.parse(uri));
//            startActivity(intent);
//        });

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getApplicationContext());
        MonitorDao monitorDao = appDatabase.monitorDao();
        CasesDao casesDao = appDatabase.casesDao();
        DoctorDao doctorDao = appDatabase.doctorDao();
        UserDao userDao = appDatabase.userDao();

        monitorsList = monitorDao.getMonitorsListByCaseId(caseId);
        doctorId = casesDao.getDoctorIdById(caseId);
        userId = doctorDao.getUserIdById(doctorId);
        fullName = userDao.getFullNameById(userId);

        doctorName.setText("Doctor: " + fullName);

        recyclerView = findViewById(R.id.monitors_list);
        adapter = new MonitorsListAdapter(this, monitorsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    //a
                    Intent homeIntent = new Intent(this, SimpleLoginActivity.class);
                    startActivity(homeIntent);
                    return true;

                case R.id.profile:
                    //b
                    /*Intent homeIntent = new Intent(this, ViewDoctor.class);
                    startActivity(homeIntent);
                    return true;*/

                case R.id.message:
                    //b
                    /*Intent homeIntent = new Intent(this, ViewMessage.class);
                    startActivity(homeIntent);
                    return true;*/

            }
            return false;
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getApplicationContext());
                MonitorDao monitorDao = appDatabase.monitorDao();
                monitorsList = monitorDao.getMonitorsListByCaseId(caseId);
                adapter.setListContent(monitorsList);
            }
        }
    }
}