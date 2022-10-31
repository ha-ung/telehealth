package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileproject.db.DoctorDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.CasesDao;

import java.security.NoSuchAlgorithmException;

public class SimpleLoginActivity extends AppCompatActivity {

    public static String LOG_TAG = SimpleLoginActivity.class.getSimpleName();
    public static final String EXTRA_ID = "com.example.android.mobileproject.extra.ID";
    private EditText inputId;
    private EditText inputPassword;
    private TextView idInputLabel;
    private Button userTypeLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_login);
        inputId = findViewById(R.id.id_input);
        inputPassword = findViewById(R.id.password_input);
        idInputLabel = findViewById(R.id.id_guide_text);
        userTypeLogin = findViewById(R.id.user_type_login_button);

        if (savedInstanceState != null) {
            idInputLabel.setText(savedInstanceState.getString("label"));
            inputId.setHint(savedInstanceState.getString("hint"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (idInputLabel.getText().toString().equals("Doctor ID")) {
            outState.putString("label", "Doctor ID");
            outState.putString("hint", "Enter doctor ID");
        }
        else if (idInputLabel.getText().toString().equals("Case ID")) {
            outState.putString("label", "Case ID");
            outState.putString("hint", "Enter case ID");
        }
    }

    public void login(View view) {
        int id = Integer.parseInt(inputId.getText().toString());
        String password = inputPassword.getText().toString();
        Log.d(LOG_TAG, String.valueOf(id));
        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getApplicationContext());
        CasesDao casesDao = appDatabase.casesDao();
        DoctorDao doctorDao = appDatabase.doctorDao();
        if (idInputLabel.getText().toString().equals("Case ID")) {
            if (casesDao.getCasesById(id) != null && casesDao.getPasswordById(id).equals(password)) {
                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (SimpleLoginActivity.this, PatientHomeActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Incorrect case ID or password", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            if (doctorDao.getDoctorById(id) != null && doctorDao.getPasswordById(id).equals(password)) {
                Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent (SimpleLoginActivity.this, DoctorHomeActivity.class);
                intent.putExtra(EXTRA_ID, id);
                startActivity(intent);
            }
            else {
                Toast.makeText(getApplicationContext(), "Incorrect doctor ID or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void loginUserType(View view) {
        if (idInputLabel.getText().toString().equals("Case ID")) {
            idInputLabel.setText("Doctor ID");
            inputId.setHint("Enter doctor ID");
            userTypeLogin.setText("Patient Login");

        }
        else {
            idInputLabel.setText("Case ID");
            inputId.setHint("Enter case ID");
            userTypeLogin.setText("Doctor Login");
        }
    }
}