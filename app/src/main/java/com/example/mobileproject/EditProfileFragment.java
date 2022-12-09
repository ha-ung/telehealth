package com.example.mobileproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.DoctorDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;

import java.util.Calendar;
import java.util.Date;

public class EditProfileFragment extends Fragment {
    private Boolean isDoctor = false;

    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText birthdayInput;
    private EditText phoneNumberInput;
    private EditText passwordInput;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton femaleButton;
    private RadioButton maleButton;

    private Integer userId;
    private Integer doctorId;
    private Integer caseId;
    private String firstName;
    private String lastName;
    private String birthday;
    private String phoneNumber;
    private String password;
    private String gender;

    private Button updateProfileButton;
    private Button selectDateButton;

    public EditProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        firstNameInput = getView().findViewById(R.id.first_name_input);
        lastNameInput = getView().findViewById(R.id.last_name_input);
        birthdayInput = getView().findViewById(R.id.birthday_input);
        phoneNumberInput = getView().findViewById(R.id.phone_input);
        passwordInput = getView().findViewById(R.id.password_input);
        updateProfileButton = getView().findViewById(R.id.button_update);
        selectDateButton = getView().findViewById(R.id.button_date);
        radioGroup = getView().findViewById(R.id.gender_group);
        femaleButton = getView().findViewById(R.id.gender_female);
        maleButton = getView().findViewById(R.id.gender_male);

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getActivity());
        UserDao userDao = appDatabase.userDao();
        DoctorDao doctorDao = appDatabase.doctorDao();
        CasesDao casesDao = appDatabase.casesDao();

        Intent intent = getActivity().getIntent();
        if (intent.hasExtra(SimpleLoginActivity.EXTRA_USER_ID)) { // patient
            userId = intent.getIntExtra(SimpleLoginActivity.EXTRA_USER_ID, 0);
            caseId = intent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);

            password = casesDao.getPasswordById(caseId);
            firstName = userDao.getFirstNameById(userId);
            lastName = userDao.getLastNameById(userId);
            birthday = userDao.getBirthDateById(userId);
            phoneNumber = userDao.getPhoneNumberById(userId);
            gender = userDao.getGenderById(userId);
        }
        else { // doctor
            isDoctor = true;
            doctorId = intent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);
            userId = doctorDao.getUserIdById(doctorId);

            password = doctorDao.getPasswordById(doctorId);
            firstName = userDao.getFirstNameById(userId);
            lastName = userDao.getLastNameById(userId);
            birthday = userDao.getBirthDateById(userId);
            phoneNumber = userDao.getPhoneNumberById(userId);
            gender = userDao.getGenderById(userId);
        }

        firstNameInput.setText(firstName);
        lastNameInput.setText(lastName);
        birthdayInput.setText(birthday);
        phoneNumberInput.setText(phoneNumber);
        passwordInput.setText(password);
        if (gender.equals("Female")) {
            femaleButton.setChecked(true);
        }
        else {
            maleButton.setChecked(true);
        }

        selectDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DATE);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.MySpinnerDatePickerStyle,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                String selectedYear;
                                String selectedMonth;
                                String selectedDate;

                                selectedYear = Integer.toString(year);
                                if (month < 9) {
                                    selectedMonth = "0" + Integer.toString(month + 1);
                                }
                                else {
                                    selectedMonth = Integer.toString(month + 1);
                                }

                                if (day < 10) {
                                    selectedDate = "0" + Integer.toString(day);
                                }
                                else {
                                    selectedDate = Integer.toString(day);
                                }
                                birthdayInput.setHint(selectedYear + "-" + selectedMonth + "-" + selectedDate);
                            }
                        }, year, month, day);

                datePickerDialog.show();
            }
        });

        updateProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newFirstName = firstNameInput.getText().toString();
                String newLastName = lastNameInput.getText().toString();
                String newPhoneNumber = phoneNumberInput.getText().toString();
                String newPassword = passwordInput.getText().toString();
                String newBirthDate = birthdayInput.getHint().toString();
                int radioButtonId = radioGroup.getCheckedRadioButtonId();
                radioButton = radioGroup.findViewById(radioButtonId);
                String newGender = radioButton.getText().toString();

                if (!isDoctor) {
                    userDao.updateProfile(userId, newFirstName, newLastName, newBirthDate, newGender, newPhoneNumber);
                    casesDao.updatePasswordById(userId, newPassword);
                }
                else {
                    userDao.updateProfile(userId, newFirstName, newLastName, newBirthDate, newGender, newPhoneNumber);
                    doctorDao.updatePasswordById(doctorId, password);
                }

                Toast.makeText(getActivity(), "Profile updated", Toast.LENGTH_SHORT).show();
            }
        });
    }
}