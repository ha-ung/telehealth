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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatboxDetailActivity extends AppCompatActivity {
    public List<Message> messagesList;
    private RecyclerView recyclerView;
    private MessagesListAdapter adapter;
    public Integer caseId;
    public EditText messageInput;
    public Integer userId;
    public Integer doctorId;
    public String userName;
    private io.socket.client.Socket socket;
    {
        try {
            socket = IO.socket("http://10.0.2.2:5000");
            Log.d("Patient Message Fragment", "Connected to socket successfully.");
        } catch (URISyntaxException e) {
            Log.d("Patient Message Fragment", "Error connecting to socket.");
        }
    }

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

        socket.connect();
        socket.on(Socket.EVENT_CONNECT, onConnect);
        socket.on("update_chat", onUpdate);
    }

    private Emitter.Listener onConnect = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = new JSONObject();
            try {
                data.put("userName", userName);
                data.put("caseId", caseId);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            socket.emit("join", data);
        }
    };

    private Emitter.Listener onUpdate = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject data = (JSONObject) args[0];
            String text;
            Integer caseId;
            String userName2 = "";
            Integer userId;
            Message message = new Message();
            try {
                userId = data.getInt("userId");
                text = data.getString("text");
                caseId = data.getInt("caseId");
                userName2 = data.getString("userName");

                message.caseId = caseId;
                message.userName = userName2;
                message.userId = userId;
                message.text = text;
                message.date = String.valueOf(LocalDate.now());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Boolean a = userName2.equals(userName);
            Log.d("userName2", userName2);
            Log.d("userName2 equals userName", a.toString());

            if (!userName2.equals(userName)) {
                addItem(message);
            }
        }
    };

    private void addItem(Message message) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messagesList.add(message);
                adapter.setListContent(messagesList);
                recyclerView.scrollToPosition(messagesList.size() - 1);
            }
        });
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

        JSONObject data = new JSONObject();
        try {
            data.put("userId", userId);
            data.put("userName", userName);
            data.put("text", text);
            data.put("caseId", caseId);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        socket.emit("message", data);

        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}