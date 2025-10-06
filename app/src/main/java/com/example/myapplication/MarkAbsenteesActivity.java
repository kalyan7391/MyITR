package com.example.myapplication;

import android.os.Bundle;
import android.widget.Toast; // Import Toast
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MarkAbsenteesActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String teacherUsername;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_absentees);

        db = new DatabaseHelper(this);
        teacherUsername = getIntent().getStringExtra("teacher_username");
        subject = getIntent().getStringExtra("subject");

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Mark Absentees for " + subject);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recycler_view_students);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<User> studentList = db.getAllStudents();
        StudentAdapter adapter = new StudentAdapter(studentList, student -> {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            db.addAttendance(student.getName(), teacherUsername, subject, date, "Absent");
            // ✨ FIX: Show a confirmation message
            Toast.makeText(this, student.getName() + " marked as absent.", Toast.LENGTH_SHORT).show();
        });

        recyclerView.setAdapter(adapter);
    }
}