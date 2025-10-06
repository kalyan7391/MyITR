package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<User> studentList;
    private OnMarkAbsentClickListener listener;

    public interface OnMarkAbsentClickListener {
        void onMarkAbsentClick(User student);
    }

    public StudentAdapter(List<User> studentList, OnMarkAbsentClickListener listener) {
        this.studentList = studentList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_student_attendance, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        User currentStudent = studentList.get(position);
        holder.tvStudentName.setText(currentStudent.getName());
        holder.btnMarkAbsent.setOnClickListener(v -> {
            listener.onMarkAbsentClick(currentStudent);
            // ✨ FIX: Disable the button after clicking
            v.setEnabled(false);
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvStudentName;
        Button btnMarkAbsent;

        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvStudentName = itemView.findViewById(R.id.tv_student_name);
            btnMarkAbsent = itemView.findViewById(R.id.btn_mark_absent);
        }
    }
}