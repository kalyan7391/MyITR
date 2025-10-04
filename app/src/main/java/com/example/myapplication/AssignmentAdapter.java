package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {

    private List<Assignment> assignmentList;

    public AssignmentAdapter(List<Assignment> assignmentList) {
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public AssignmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_assignment, parent, false);
        return new AssignmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssignmentViewHolder holder, int position) {
        Assignment currentAssignment = assignmentList.get(position);
        holder.tvAssignmentTitle.setText(currentAssignment.getTitle());
        holder.tvDueDate.setText("Due: " + currentAssignment.getDueDate());
        holder.tvSubmissionStatus.setText(currentAssignment.getStatus());

        // Change color based on status
        if ("Submitted".equals(currentAssignment.getStatus())) {
            holder.tvSubmissionStatus.setTextColor(Color.parseColor("#34A853")); // Green
        } else {
            holder.tvSubmissionStatus.setTextColor(Color.parseColor("#D32F2F")); // Red
        }
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssignmentTitle, tvDueDate, tvSubmissionStatus;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAssignmentTitle = itemView.findViewById(R.id.tv_assignment_title);
            tvDueDate = itemView.findViewById(R.id.tv_due_date);
            tvSubmissionStatus = itemView.findViewById(R.id.tv_submission_status);
        }
    }
}