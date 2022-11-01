package com.example.mobileproject;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobileproject.db.Cases;

import java.util.ArrayList;
import java.util.List;

public class CasesListAdapter extends RecyclerView.Adapter<CasesListAdapter.CaseItemHolder> {
    private final List<Cases> casesList;
    private LayoutInflater inflater;
    public static final String EXTRA_ID = "com.example.android.mobileproject.extra.ID";

    public CasesListAdapter(Context context, List<Cases> casesList) {
        this.casesList = casesList;
        inflater = LayoutInflater.from(context);
    }

    public class CaseItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView caseItem;
        public final CasesListAdapter adapter;

        public CaseItemHolder(@NonNull View itemView, CasesListAdapter adapter) {
            super(itemView);
            caseItem = itemView.findViewById(R.id.case_item);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            int position = getLayoutPosition();
            Integer current = casesList.get(position).caseId;
            Intent intent = new Intent(context, CaseDetailActivity.class);
            intent.putExtra(EXTRA_ID, current);
            context.startActivity(intent);
        }
    }

    @NonNull
    @Override
    public CasesListAdapter.CaseItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.case_item, parent, false);
        return new CaseItemHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CasesListAdapter.CaseItemHolder holder, int position) {
        Integer current = casesList.get(position).caseId;
        holder.caseItem.setText("Case #" + current.toString());
    }

    @Override
    public int getItemCount() {
        return casesList.size();
    }
}
