package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.stream.Collectors;

public class ViewTeacherAttendanceActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String teacherUsername;

    private Spinner spinnerSubjects;
    private RecyclerView recyclerView;
    private AttendanceRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_teacher_attendance);

        db = new DatabaseHelper(this);
        teacherUsername = getIntent().getStringExtra("username");

        Toolbar toolbar = findViewById(R.id.toolbar);
        spinnerSubjects = findViewById(R.id.spinner_subjects);
        recyclerView = findViewById(R.id.recycler_view_attendance);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        toolbar.setNavigationOnClickListener(v -> finish());

        loadSubjectsIntoSpinner();

        spinnerSubjects.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedSubject = parent.getItemAtPosition(position).toString();
                loadAttendanceRecords(selectedSubject);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void loadSubjectsIntoSpinner() {
        List<ClassItem> classes = db.getClassesForTeacher(teacherUsername);
        List<String> subjectNames = classes.stream()
                .map(ClassItem::getName)
                .distinct()
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubjects.setAdapter(adapter);
    }

    private void loadAttendanceRecords(String subject) {
        List<AttendanceRecord> records = db.getAttendanceForSubject(subject, teacherUsername);
        adapter = new AttendanceRecordAdapter(records);
        recyclerView.setAdapter(adapter);
    }
}