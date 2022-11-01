package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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
    String TELEPHONE="0123456789";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);

        fab = findViewById(R.id.callButton);
        bottomNav = findViewById(R.id.bottomNavigationView);
        //linear = findViewById(R.id.announcement);

        fab.setOnClickListener(v -> { //View v
            Intent intent = new Intent(Intent.ACTION_DIAL);
            String uri = "tel:" + TELEPHONE;
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        });

        /*BadgeDrawable badge1 = bottomNav.getOrCreateBadge(R.id.home);
        badge1.setVisible(true);*/

        bottomNav.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    //a
                    /*
                    Intent homeIntent = new Intent(this, SimpleLoginActivity.class);
                    startActivity(homeIntent);
                    return true;*/
                    PatientHomeActivity_HomeFragment homeFragment = new PatientHomeActivity_HomeFragment();
                    FragmentTransaction homeTransaction = getSupportFragmentManager().beginTransaction();
                    homeTransaction.replace(R.id.content, homeFragment, "");
                    homeTransaction.commit();

                case R.id.profile:
                    //b
                    /*Intent homeIntent = new Intent(this, ViewDoctor.class);
                    startActivity(homeIntent);
                    return true;*/
                    PatientHomeActivity_ProfileFragment profileFragment = new PatientHomeActivity_ProfileFragment();
                    FragmentTransaction profileTransaction = getSupportFragmentManager().beginTransaction();
                    profileTransaction.replace(R.id.content, profileFragment, "");
                    profileTransaction.commit();

                case R.id.message:
                    //b
                    /*Intent homeIntent = new Intent(this, ViewMessage.class);
                    startActivity(homeIntent);
                    return true;*/
                    PatientHomeActivity_MessageFragment messageFragment = new PatientHomeActivity_MessageFragment();
                    FragmentTransaction messageTransaction = getSupportFragmentManager().beginTransaction();
                    messageTransaction.replace(R.id.content, messageFragment, "");
                    messageTransaction.commit();

            }
            return false;
        });

        //Adding additional TextView
        /*
        TextView txt = new TextView(this);
        txt.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        txt.setText("");
        linear.addView(txt);
         */
    }
}