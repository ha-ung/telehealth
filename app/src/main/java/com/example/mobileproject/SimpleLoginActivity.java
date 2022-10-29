package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.CasesDao;

public class SimpleLoginActivity extends AppCompatActivity {

    public static String LOG_TAG = SimpleLoginActivity.class.getSimpleName();

    //public static final String EXTRA_MESSAGE = "com.example.android.app.extra.MESSAGE";

    //private EditText username;
    //private EditText password;
    private EditText caseId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);

        caseId = findViewById(R.id.login_input);
    }

    public Boolean validateInput(Integer input) {
        if (input <= 0) {
            return false;
        }
        else {
            return true;
        }
    }

    public void login(View view) {
        int id = Integer.parseInt(caseId.getText().toString());
        if (validateInput(id)) {
            Log.d(LOG_TAG, String.valueOf(id));
            TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getApplicationContext());
            CasesDao casesDao = appDatabase.casesDao();
            if (casesDao.getCasesById(id) != null) {
                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (SimpleLoginActivity.this, PatientHomeActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Case ID doesn't exist", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Please enter a valid case ID", Toast.LENGTH_SHORT).show();
        }
    }
}