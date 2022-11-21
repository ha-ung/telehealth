package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobileproject.db.Doctor;
import com.example.mobileproject.db.DoctorDao;
import com.example.mobileproject.db.Message;
import com.example.mobileproject.db.MessageDao;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;

import java.time.LocalDate;
import java.util.List;

public class ChatboxDetailActivity extends AppCompatActivity {
    public List<Message> messagesList;
    private RecyclerView recyclerView;
    private MessagesListAdapter adapter;
    public Integer caseId;
    public EditText messageInput;
    public Integer userId;
    public Integer doctorId;
    public String userName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbox_detail);

        messageInput = findViewById(R.id.message_input);

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(this);
        MessageDao messageDao = appDatabase.messageDao();
        DoctorDao doctorDao = appDatabase.doctorDao();
        UserDao userDao = appDatabase.userDao();

        Intent caseIdIntent = getIntent();
        caseId = caseIdIntent.getIntExtra(CaseChatboxListAdapter.EXTRA_ID, 0);
        Intent userIdIntent = getIntent();
        doctorId = userIdIntent.getIntExtra(SimpleLoginActivity.EXTRA_ID, 0);
        userId = doctorDao.getUserIdById(doctorId);
        userName = userDao.getFullNameById(userId);
        messagesList = messageDao.getMessageByCaseId(caseId);

        recyclerView = findViewById(R.id.messages_list);
        adapter = new MessagesListAdapter(this, messagesList);
        recyclerView.scrollToPosition(messagesList.size() - 1);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void sendMessage(View view) {
        String text = messageInput.getText().toString();

        TelehealthDatabase appDatabase = TelehealthDatabase.getDbInstance(this);
        MessageDao messageDao = appDatabase.messageDao();

        Message message = new Message();
        message.caseId = caseId;
        message.userName = userName;
        message.userId = userId;
        message.text = text;
        message.date = String.valueOf(LocalDate.now());

        messageDao.insertMessage(message);
        Toast.makeText(this, "Message sent", Toast.LENGTH_SHORT);

        messagesList = messageDao.getMessageByCaseId(caseId);
        adapter.setListContent(messagesList);
        recyclerView.scrollToPosition(messagesList.size() - 1);
        messageInput.setText("");

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}