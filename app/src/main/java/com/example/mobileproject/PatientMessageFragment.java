package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobileproject.db.CasesDao;
import com.example.mobileproject.db.Message;
import com.example.mobileproject.db.MessageDao;
import com.example.mobileproject.db.Monitor;
import com.example.mobileproject.db.MonitorDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;

import java.time.LocalDate;
import java.util.List;

public class PatientMessageFragment extends Fragment implements View.OnClickListener {
    public List<Message> messagesList;
    private RecyclerView recyclerView;
    private MessagesListAdapter adapter;
    public Integer caseId;
    public EditText messageInput;
    public Integer userId;
    public String userName;
    public Button sendButton;

    public PatientMessageFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patient_message, container, false);
        sendButton = (Button) view.findViewById(R.id.send_button);
        messageInput = (EditText) view.findViewById(R.id.message_input);
        sendButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getActivity());
        MessageDao messageDao = appDatabase.messageDao();
        UserDao userDao = appDatabase.userDao();

        Intent caseIdIntent = getActivity().getIntent();
        caseId = caseIdIntent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);
        Intent userIdIntent = getActivity().getIntent();
        userId = userIdIntent.getIntExtra(SimpleLoginActivity.EXTRA_USER_ID, 0);
        userName = userDao.getFullNameById(userId);
        messagesList = messageDao.getMessageByCaseId(caseId);

        recyclerView = (RecyclerView) getView().findViewById(R.id.messages_list);
        adapter = new MessagesListAdapter(getActivity(), messagesList);
        recyclerView.scrollToPosition(messagesList.size() - 1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onClick(View view) {
        String text = messageInput.getText().toString();

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(getActivity());
        MessageDao messageDao = appDatabase.messageDao();

        Message message = new Message();
        message.caseId = caseId;
        message.userName = userName;
        message.userId = userId;
        message.text = text;
        message.date = String.valueOf(LocalDate.now());

        messageDao.insertMessage(message);
        Toast.makeText(getActivity(), "Message sent", Toast.LENGTH_SHORT);

        messagesList = messageDao.getMessageByCaseId(caseId);
        adapter.setListContent(messagesList);
        recyclerView.scrollToPosition(messagesList.size() - 1);
        messageInput.setText("");

        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}