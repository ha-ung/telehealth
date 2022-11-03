package com.example.mobileproject;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileproject.db.Monitor;
import com.example.mobileproject.db.MonitorDao;
import com.example.mobileproject.db.TelehealthDatabase;

import java.util.List;

public class MonitorsListAdapter extends RecyclerView.Adapter<MonitorsListAdapter.MonitorListHolder> {
    private List<Monitor> monitorsList;
    private LayoutInflater inflater;

    public MonitorsListAdapter(Context context, List<Monitor> monitorsList) {
        this.monitorsList = monitorsList;
        inflater = LayoutInflater.from(context);
    }

    public class MonitorListHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView symptom;
        public TextView note;
        public TextView date;
        public final MonitorsListAdapter adapter;

        public MonitorListHolder(View itemView, MonitorsListAdapter adapter) {
            super(itemView);
            title = itemView.findViewById(R.id.monitor_title);
            symptom = itemView.findViewById(R.id.monitor_symptoms);
            note = itemView.findViewById(R.id.monitor_note);
            date = itemView.findViewById(R.id.monitor_date);
            this.adapter = adapter;
        }
    }

    @NonNull
    @Override
    public MonitorsListAdapter.MonitorListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.monitor_item, parent, false);
        return new MonitorListHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull MonitorsListAdapter.MonitorListHolder holder, int position) {
        Integer current = monitorsList.get(position).monitorId;

        String symptom = monitorsList.get(position).symptom;

        String note = monitorsList.get(position).note;
        String date = monitorsList.get(position).date;

        holder.title.setText("Monitor #" + String.valueOf(current));
        holder.symptom.setText("Symptom: " + symptom);
        holder.note.setText("Note: " + note);
        holder.date.setText("Date: " + date);
    }

    public void setListContent(List<Monitor> monitorsList) {
        this.monitorsList = monitorsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return monitorsList.size();
    }
}
