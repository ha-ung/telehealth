package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class PatientHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    PatientHomeFragment patientHomeFragment;
    ProfileFragment profileFragment;
    PatientMessageFragment patientMessageFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        patientHomeFragment = new PatientHomeFragment();
        profileFragment = new ProfileFragment();
        patientMessageFragment = new PatientMessageFragment();

        bottomNavigationView = findViewById(R.id.bottom_nav);
        replaceFragment(R.id.HomeFragment);
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
                        replaceFragment(R.id.HomeFragment);
                        return true;
                    case R.id.profile:
                        replaceFragment(R.id.ProfileFragment);
                        return true;
                    case R.id.message:
                        replaceFragment(R.id.MessageFragment);
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
                return true;
            case R.id.call_hospital:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "0123456789"));
                startActivity(intent);
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
}