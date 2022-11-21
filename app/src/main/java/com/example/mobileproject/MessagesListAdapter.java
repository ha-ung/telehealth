package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileproject.db.Message;
import com.example.mobileproject.db.TelehealthDatabase;
import com.example.mobileproject.db.UserDao;

import java.util.List;

public class MessagesListAdapter extends RecyclerView.Adapter<MessagesListAdapter.MessageItemHolder> {
    private List<Message> messagesList;
    private LayoutInflater inflater;
    public static final String EXTRA_ID = "com.example.android.mobileproject.extra.ID";

    public MessagesListAdapter(Context context, List<Message> messagesList) {
        this.messagesList = messagesList;
        inflater = LayoutInflater.from(context);
    }

    public class MessageItemHolder extends RecyclerView.ViewHolder {
        public TextView userName;
        public TextView text;
        public TextView date;
        public final MessagesListAdapter adapter;

        public MessageItemHolder(@NonNull View itemView, MessagesListAdapter adapter) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_id_text);
            text = itemView.findViewById(R.id.message_text);
            date = itemView.findViewById(R.id.message_date);
            this.adapter = adapter;
        }
    }

    @NonNull
    @Override
    public MessagesListAdapter.MessageItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.message_item, parent, false);
        return new MessagesListAdapter.MessageItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesListAdapter.MessageItemHolder holder, int position) {
        String userName = messagesList.get(position).userName;
        String text = messagesList.get(position).text;
        String date = messagesList.get(position).date;

        holder.userName.setText(userName.toString());
        holder.text.setText(text);
        holder.date.setText(date);
    }

    public void setListContent(List<Message> messagesList) {
        this.messagesList = messagesList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return messagesList.size();
    }
}