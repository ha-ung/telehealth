package com.example.mobileproject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.DoctorDao;
import com.example.mobileproject.db.PatientDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;

import java.util.Calendar;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    private EditText birthday_input;

    private EditText gender_input;

    private EditText firstname;
    private EditText lastname;
    private EditText birthday;
    private EditText phone;
    private EditText password;

    private Intent intent;
    private Integer caseId;
    private Integer userId;

    private int lastSelectedYear;
    private int lastSelectedMonth;
    private int lastSelectedDayOfMonth;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Button btnDisplay;

    private static final int PATIENT = 1;
    private static final int DOCTOR = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientHomeActivity_ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = requireActivity().getIntent();
        caseId = intent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);
        userId = intent.getIntExtra(SimpleLoginActivity.EXTRA_USER_ID, 0);

    }

//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//
//        addListenerOnButton();
//
//        this.birthday_input = (EditText) requireView().findViewById(R.id.birthday_input);
//        firstname = (EditText) requireView().findViewById(R.id.first_name_input);
//        lastname = (EditText) requireView().findViewById(R.id.last_name_input);
//        birthday = (EditText) requireView().findViewById(R.id.birthday_input);
//        phone = (EditText) requireView().findViewById(R.id.phone_input);
//        password = (EditText) requireView().findViewById(R.id.password_input);
//
//        Button buttonDate = (Button) getView().findViewById(R.id.button_date);
//        Button buttonUpdate = (Button) getView().findViewById(R.id.button_update);
//
//        buttonDate.setOnClickListener(v -> buttonSelectDate());
//        buttonUpdate.setOnClickListener(v -> updateProfile());
//
//
//        // Get Current Date
//        final Calendar c = Calendar.getInstance();
//        this.lastSelectedYear = c.get(Calendar.YEAR);
//        this.lastSelectedMonth = c.get(Calendar.MONTH);
//        this.lastSelectedDayOfMonth = c.get(Calendar.DAY_OF_MONTH);
//    }
//
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    public void updateProfile(View view) {
    }
//
//    // User click on 'Select Date' button.
//    private void buttonSelectDate() {
//
//        // Date Select Listener.
//        DatePickerDialog.OnDateSetListener dateSetListener = (view, year, monthOfYear, dayOfMonth) -> {
//
//            birthday_input.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
//
//            lastSelectedYear = year;
//            lastSelectedMonth = monthOfYear;
//            lastSelectedDayOfMonth = dayOfMonth;
//        };
//
//        DatePickerDialog datePickerDialog =  new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar, dateSetListener, lastSelectedYear, lastSelectedMonth, lastSelectedDayOfMonth);
//
//        // Show
//        datePickerDialog.show();
//    }
//
//    public void addListenerOnButton() {
//
//        radioGroup = (RadioGroup) getView().findViewById(R.id.gender_group);
//
//        btnDisplay.setOnClickListener(v -> {
//
//            // get selected radio button from radioGroup
//            int selectedId = radioGroup.getCheckedRadioButtonId();
//
//            // find the radiobutton by returned id
//            radioButton = (RadioButton) getView().findViewById(selectedId);
//
//            gender_input = (EditText) radioButton.getText();
//
//        });
//    }
//
//    public void updateProfile() {
//        TelehealthDatabase db = TelehealthDatabase.getDbInstance(getActivity());
//
//        CasesDao casesDao = db.casesDao();
//        UserDao userDao = db.userDao();
//        PatientDao patientDao = db.patientDao();
//        DoctorDao doctorDao = db.doctorDao();
//
//        int user;
//
//        if (userId == 0 && caseId != 0) {
//            user = DOCTOR;
//        } else {
//            user = PATIENT;
//        }
//        if (user == DOCTOR) {
//            Integer doctorId = caseId;
//            userId = doctorDao.getUserIdById(doctorId);
//        }
//
//        userDao.updateProfile(userId, firstname.getText().toString(), lastname.getText().toString(), birthday.getText().toString(), phone.getText().toString());
//        Integer patientId = patientDao.getPatientIdById(userId);
//        userDao.updatePassword(patientId, password.getText().toString());
//    }
}