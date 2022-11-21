package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileproject.db.Cases;
import com.example.mobileproject.db.Message;

import java.util.List;

public class CaseChatboxListAdapter extends RecyclerView.Adapter<CaseChatboxListAdapter.CaseChatboxItemHolder> {
    private List<Cases> caseChatboxList;
    private LayoutInflater inflater;
    public static final String EXTRA_ID = "com.example.android.mobileproject.extra.ID";

    public CaseChatboxListAdapter(Context context, List<Cases> caseChatboxList) {
        this.caseChatboxList = caseChatboxList;
        inflater = LayoutInflater.from(context);
    }

    public class CaseChatboxItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView caseId;
        public final CaseChatboxListAdapter adapter;

        public CaseChatboxItemHolder(@NonNull View itemView, CaseChatboxListAdapter adapter) {
            super(itemView);
            caseId = itemView.findViewById(R.id.case_chatbox_item);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            int position = getLayoutPosition();
            Integer current = caseChatboxList.get(position).caseId;
            Intent intent = new Intent(context, ChatboxDetailActivity.class);
            intent.putExtra(EXTRA_ID, current);
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CaseChatboxListAdapter.CaseChatboxItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.case_chatbox_item, parent, false);
        return new CaseChatboxListAdapter.CaseChatboxItemHolder(itemView, this);
    }


    @Override
    public void onBindViewHolder(@NonNull CaseChatboxListAdapter.CaseChatboxItemHolder holder, int position) {
        String caseId = "Case #" + caseChatboxList.get(position).caseId;

        holder.caseId.setText(caseId);
    }

    public void setListContent(List<Cases> caseChatboxList) {
        this.caseChatboxList = caseChatboxList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return caseChatboxList.size();
    }
}
