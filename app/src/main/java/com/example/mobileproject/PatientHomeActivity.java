package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class PatientHomeActivity extends AppCompatActivity {

    FloatingActionButton fab;
    BottomNavigationView bottomNav;
    LinearLayout linear;
    int TELEPHONE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        fab = findViewById(R.id.callButton);
        bottomNav = findViewById(R.id.bottomNavigationView);
        linear = findViewById(R.id.announcement);

        BadgeDrawable badge1 = bottomNav.getOrCreateBadge(R.id.option1);
        badge1.setVisible(true);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                String uri = "tel"+TELEPHONE;
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.option1:
                    return true;
            }
            return false;
        });
    }
}