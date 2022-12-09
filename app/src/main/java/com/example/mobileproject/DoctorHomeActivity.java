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

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DoctorHomeActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    DoctorHomeFragment doctorHomeFragment;
    EditProfileFragment profileFragment;
    DoctorMessageFragment doctorMessageFragment;
    private int exitCount;
    private String current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);

        exitCount = 0;

        doctorHomeFragment = new DoctorHomeFragment();
        profileFragment = new EditProfileFragment();
        doctorMessageFragment= new DoctorMessageFragment();

        bottomNavigationView = findViewById(R.id.bottom_nav_doctor);
        /*
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.frame_layout_doctor);
        NavController navController = navHostFragment.getNavController();*/
        replaceFragment(R.id.DoctorHomeFragment);
        current = "Home";

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        if (current.equals("Message"))
                            replaceFragment(R.id.action_DoctorMessageFragment_to_DoctorHomeFragment);
                        else {
                            if (current.equals("Profile"))
                                replaceFragment(R.id.action_ProfileFragment_to_DoctorHomeFragment);
                        }
                        current = "Home";
                        return true;
                    case R.id.profile:
                        if (current.equals("Home"))
                            replaceFragment(R.id.action_DoctorHomeFragment_to_ProfileFragment);
                        else {
                            if (current.equals("Message"))
                                replaceFragment(R.id.action_DoctorMessageFragment_to_ProfileFragment);
                        }
                        current = "Profile";
                        return true;
                    case R.id.message:
                        if (current.equals("Home"))
                            replaceFragment(R.id.action_DoctorHomeFragment_to_DoctorMessageFragment);
                        else {
                            if (current.equals("Profile"))
                                replaceFragment(R.id.action_ProfileFragment_to_DoctorMessageFragment);
                        }
                        current = "Message";
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
                startActivity(requestCheckUpIntent);
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
                        .findFragmentById(R.id.frame_layout_doctor);
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