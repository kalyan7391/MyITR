// In TeacherActivity.java

package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button; // ✨ 1. IMPORT THE BUTTON CLASS
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TeacherActivity extends AppCompatActivity {
    TextView tvWelcome;
    String username;
    Button btnViewNotifications, btnMarkAttendance; // ✨ 2. DECLARE THE BUTTON VARIABLE HERE

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        tvWelcome = findViewById(R.id.tvWelcome);
        username = getIntent().getStringExtra("username");
        tvWelcome.setText("Welcome " + username);

        btnViewNotifications = findViewById(R.id.btnViewNotifications);
        btnMarkAttendance = findViewById(R.id.btnMarkAttendance); // ✨ 3. INITIALIZE THE BUTTON

        btnViewNotifications.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, NotificationActivity.class);
            i.putExtra("userType", "teacher");
            startActivity(i);
        });

        // This line will now work correctly
        btnMarkAttendance.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, MarkAttendanceOptionsActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });
    }
}