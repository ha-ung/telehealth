package com.example.mobileproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileproject.db.Cases;
import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.TelehealthDatabase;

import java.util.List;

public class DoctorHomeFragment extends Fragment {
    private List<Cases> casesList;
    private RecyclerView recyclerView;
    private CasesListAdapter adapter;

    public DoctorHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        int id = intent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getActivity());
        CasesDao casesDao = appDatabase.casesDao();
        casesList = casesDao.getAllCasesByDoctorId(id);

        recyclerView = (RecyclerView) getView().findViewById(R.id.cases_list);
        adapter = new CasesListAdapter(getActivity(), casesList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}