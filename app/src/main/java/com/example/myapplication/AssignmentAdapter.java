package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
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
        holder.tvTeacherName.setText("Published by: " + currentAssignment.getTeacherName());

        String status = currentAssignment.getStatus() != null ? currentAssignment.getStatus() : "Not Submitted";
        holder.tvSubmissionStatus.setText(status);

        if ("Submitted".equals(status)) {
            holder.tvSubmissionStatus.setTextColor(Color.parseColor("#34A853"));
        } else {
            holder.tvSubmissionStatus.setTextColor(Color.parseColor("#D32F2F"));
        }

        holder.itemView.setOnClickListener(v -> {
            String filePath = currentAssignment.getFilePath();
            if (filePath != null && !filePath.isEmpty()) {
                Uri fileUri = Uri.parse(filePath);
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(context, "Cannot open file. No application found to handle it.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(holder.itemView.getContext(), "No file attached to this assignment.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    static class AssignmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvAssignmentTitle, tvDueDate, tvSubmissionStatus, tvTeacherName;

        public AssignmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAssignmentTitle = itemView.findViewById(R.id.tv_assignment_title);
            tvDueDate = itemView.findViewById(R.id.tv_due_date);
            tvSubmissionStatus = itemView.findViewById(R.id.tv_submission_status);
            tvTeacherName = itemView.findViewById(R.id.tv_teacher_name);
        }
    }
}