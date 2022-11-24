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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class PatientMessageFragment extends Fragment implements View.OnClickListener {
    public List<Message> messagesList;
    private RecyclerView recyclerView;
    private MessagesListAdapter adapter;
    public Integer caseId;
    public EditText messageInput;
    public Integer userId;
    public String userName;
    public Button sendButton;
    private io.socket.client.Socket socket;
    {
        try {
            socket = IO.socket("http://10.0.2.2:5000");
            Log.d("Patient Message Fragment", "Connected to socket successfully.");
        } catch (URISyntaxException e) {
            Log.d("Patient Message Fragment", "Error connecting to socket.");
        }
    }


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
    public void onDestroy() {
        super.onDestroy();
        socket.disconnect();

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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                messagesList.add(message);
                adapter.setListContent(messagesList);
                //recyclerView.scrollToPosition(messagesList.size() - 1);
            }
        });
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

        InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}