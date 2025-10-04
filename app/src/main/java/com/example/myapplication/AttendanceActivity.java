package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    private ListView lvAttendance;
    private DatabaseHelper db;
    private String studentUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        lvAttendance = findViewById(R.id.lvAttendance);
        db = new DatabaseHelper(this);
        studentUsername = getIntent().getStringExtra("username");

        // Get the attendance records from the database
        List<String> attendanceRecords = db.getAttendance(studentUsername);

        // Use an adapter to show the records in the list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, attendanceRecords);
        lvAttendance.setAdapter(adapter);
    }
}