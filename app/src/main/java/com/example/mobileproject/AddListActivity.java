package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileproject.db.Monitor;
import com.example.mobileproject.db.MonitorDao;
import com.example.mobileproject.db.Patient;
import com.example.mobileproject.db.TelehealthDatabase;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddListActivity extends AppCompatActivity {
    private Spinner spinner;
    private List<String> items;
    private MultiSpinner multiSpinner;
    private MultiSpinner.MultiSpinnerListener listener;
    private EditText noteInput;

    private String symptoms;
    private String note;
    private String date;
    private Integer caseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_list);

        Intent intent = getIntent();
        caseId = intent.getIntExtra(PatientHomeFragment.EXTRA_ID, 0);
        noteInput = findViewById(R.id.note_input);
        symptoms  = "";
        date = String.valueOf(LocalDate.now());
        items = Arrays.asList(getResources().getStringArray(R.array.symptoms_array));

        multiSpinner = (MultiSpinner) findViewById(R.id.symptoms_spinner);
        multiSpinner.setItems(items, "Log your symptoms", listener);

    }

    public void submit(View view) {
        boolean[] selected = multiSpinner.getSelected();

        for (int i = 0; i < selected.length; i++) {
            if (selected[i] == true) {
                symptoms += items.get(i) + ", ";
            }
        }

        symptoms = symptoms.substring(0, symptoms.lastIndexOf(","));
        note = noteInput.getText().toString();

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getApplicationContext());
        MonitorDao monitorDao = appDatabase.monitorDao();
        Monitor monitor = new Monitor();
        monitor.symptom = symptoms;
        monitor.note = note;
        monitor.date = date;
        monitor.caseId = caseId;
        monitorDao.insertMonitor(monitor);

        Toast.makeText(getApplicationContext(), "Monitor list added", Toast.LENGTH_SHORT);
        Intent replyIntent = new Intent();
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}