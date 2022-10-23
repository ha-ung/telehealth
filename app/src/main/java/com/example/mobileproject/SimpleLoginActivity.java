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
    private EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);

        username = findViewById(R.id.usernameInput);
        password = findViewById(R.id.passwordInput);

        Log.d(LOG_NAME, "Entered Login Screen");
    }

    public void login(View view) throws NoSuchAlgorithmException {
        Log.d(LOG_NAME, "Login into account");

        Intent login = new Intent(this, PatientHomeActivity.class);
        String usernameIN = username.getText().toString();
        String passwordIN = password.getText().toString();

        String encryptPasswordIN = hashPassword(passwordIN);

        login.putExtra(EXTRA_MESSAGE, usernameIN);
        login.putExtra(EXTRA_MESSAGE, encryptPasswordIN);

        startActivity(login);
    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException{

        MessageDigest md = MessageDigest.getInstance("SHA-512");
        md.reset();
        md.update(password.getBytes());
        byte[] mdArray = md.digest();
        StringBuilder sb = new StringBuilder(mdArray.length * 2);
        for(byte b : mdArray) {
            int v = b & 0xff;
            if(v < 16)
                sb.append('0');
            sb.append(Integer.toHexString(v));
        }

        return sb.toString();
    }

    public static String getSalt() throws NoSuchAlgorithmException {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[32];
        sr.nextBytes(salt);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return Base64.getEncoder().encodeToString(salt);
        }
        return null;
    }

}