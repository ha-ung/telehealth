package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class SimpleLoginActivity extends AppCompatActivity {

    public static String LOG_NAME = SimpleLoginActivity.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "com.example.android.app.extra.MESSAGE";

    private EditText username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);
        username = findViewById(R.id.usernameInput);
        Log.d(LOG_NAME, "Entered Login Screen");
    }

    public void login(View view) throws NoSuchAlgorithmException {
        Log.d(LOG_NAME, "Login into account");
        Intent login = new Intent(this, PatientHomeActivity.class);
        String usernameIN = username.getText().toString();
        login.putExtra(EXTRA_MESSAGE, usernameIN);
        startActivity(login);
    }
}