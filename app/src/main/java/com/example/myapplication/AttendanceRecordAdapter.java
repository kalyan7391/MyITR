package com.example.myapplication;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AttendanceRecordAdapter extends RecyclerView.Adapter<AttendanceRecordAdapter.ViewHolder> {

    private List<AttendanceRecord> records;

    public AttendanceRecordAdapter(List<AttendanceRecord> records) {
        this.records = records;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_attendance_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AttendanceRecord record = records.get(position);
        holder.studentName.setText(record.getStudentName());
        holder.date.setText("Date: " + record.getDate());
        holder.status.setText(record.getStatus());

        if ("Present".equalsIgnoreCase(record.getStatus())) {
            holder.status.setTextColor(Color.parseColor("#34A853")); // Green
        } else {
            holder.status.setTextColor(Color.parseColor("#D32F2F")); // Red
        }
    }

    @Override
    public int getItemCount() {
        return records.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView studentName, date, status;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.tv_student_name);
            date = itemView.findViewById(R.id.tv_attendance_date);
            status = itemView.findViewById(R.id.tv_attendance_status);
        }
    }
}