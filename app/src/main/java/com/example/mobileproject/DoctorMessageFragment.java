package com.example.mobileproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileproject.db.Cases;
import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.DoctorDao;
import com.example.mobileproject.db.Message;
import com.example.mobileproject.db.TelehealthDatabase;

import java.util.List;

public class DoctorMessageFragment extends Fragment {
    public List<Cases> caseChatboxList;
    private RecyclerView recyclerView;
    private CaseChatboxListAdapter adapter;
    private Integer doctorId;

    public DoctorMessageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_message, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getActivity());
        CasesDao casesDao = appDatabase.casesDao();

        Intent intent = getActivity().getIntent();
        doctorId = intent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);
        caseChatboxList = casesDao.getAllCasesByDoctorId(doctorId);

        recyclerView = (RecyclerView) getView().findViewById(R.id.case_chatbox_list);
        adapter = new CaseChatboxListAdapter(getActivity(), caseChatboxList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}