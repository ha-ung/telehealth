package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PatientHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    PatientHomeFragment patientHomeFragment;
    ProfileFragment profileFragment;
    PatientMessageFragment patientMessageFragment;
    private String doctorPhoneNumber;
    private Integer caseId;
    private int exitCount;
    public static final String EXTRA_ID = "com.example.android.mobileproject.extra.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        exitCount = 0;

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(this);
        CasesDao casesDao = appDatabase.casesDao();

        Intent intent = getIntent();
        caseId = intent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);
        doctorPhoneNumber = casesDao.getDoctorPhoneNumberByCaseId(caseId);

        patientHomeFragment = new PatientHomeFragment();
        profileFragment = new ProfileFragment();
        patientMessageFragment = new PatientMessageFragment();

        bottomNavigationView = findViewById(R.id.bottom_nav_patient);
        replaceFragment(R.id.PatientHomeFragment);
        /*
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.frame_layout);
        NavController navController = navHostFragment.getNavController();*/
        //replaceFragment(patientHomeFragment);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        replaceFragment(R.id.PatientHomeFragment);
                        return true;
                    case R.id.profile:
                        replaceFragment(R.id.PatientProfileFragment);
                        return true;
                    case R.id.message:
                        replaceFragment(R.id.PatientMessageFragment);
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.request_checkup:
                Intent requestCheckUpIntent = new Intent(this, VideoCallActivity.class);
                requestCheckUpIntent.putExtra(EXTRA_ID, doctorPhoneNumber);
                startActivity(requestCheckUpIntent);
                return true;
            case R.id.call_hospital:
                Intent callHospitalIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0123456789"));
                startActivity(callHospitalIntent);
                return true;
        }
        return false;
    }

    public void replaceFragment(int destinationID) {
        /*
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();*/
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.frame_layout_patient);
        NavController navController = navHostFragment.getNavController();
        navController.navigate(destinationID);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        exitCount++;
        if (exitCount == 1) {
            Toast.makeText(this, "Press Back again to exit", Toast.LENGTH_SHORT).show();
        } else {
            Intent exit = new Intent(Intent.ACTION_MAIN);
            exit.addCategory(Intent.CATEGORY_HOME);
            exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(exit);
        }
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                exitCount = 0;
            }
        }, 1000);
    }
}