package com.example.mobileproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.DoctorDao;
import com.example.mobileproject.db.Monitor;
import com.example.mobileproject.db.MonitorDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class PatientHomeFragment extends Fragment {
    public PatientHomeFragment() {
        // Required empty public constructor
    }

    public static final String EXTRA_ID = "com.example.android.mobileproject.extra.ID";
    public static final int TEXT_REQUEST = 1;
    public FloatingActionButton addList;

    private static RecyclerView recyclerView;
    private static MonitorsListAdapter adapter;

    private List<Monitor> monitorsList;
    private Integer doctorId;
    private Integer userId;
    private String fullName;
    private Integer caseId;
    private TextView doctorName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_patient_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        doctorName = (TextView) getView().findViewById(R.id.doctor_name);

        Intent intentId = getActivity().getIntent();
        caseId = intentId.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);

        addList = (FloatingActionButton) getView().findViewById(R.id.add_list_button);
        addList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addList = new Intent(getActivity(), AddListActivity.class);
                addList.putExtra(EXTRA_ID, caseId);
                startActivityForResult(addList, TEXT_REQUEST);
            }
        });

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getActivity());
        MonitorDao monitorDao = appDatabase.monitorDao();
        CasesDao casesDao = appDatabase.casesDao();
        DoctorDao doctorDao = appDatabase.doctorDao();
        UserDao userDao = appDatabase.userDao();

        monitorsList = monitorDao.getMonitorsListByCaseId(caseId);
        doctorId = casesDao.getDoctorIdById(caseId);
        userId = doctorDao.getUserIdById(doctorId);
        fullName = userDao.getFullNameById(userId);

        doctorName.setText("Doctor: " + fullName);

        recyclerView = (RecyclerView) getView().findViewById(R.id.monitors_list);
        adapter = new MonitorsListAdapter(getActivity(), monitorsList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getActivity());
                MonitorDao monitorDao = appDatabase.monitorDao();
                monitorsList = monitorDao.getMonitorsListByCaseId(caseId);
                adapter.setListContent(monitorsList);
            }
        }
    }
}