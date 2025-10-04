package com.example.myapplication;

import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    Button btnAddTeacher, btnAddStudent, btnManageTeacher, btnManageStudent,btnSendNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        btnAddTeacher = findViewById(R.id.btnAddTeacher);
        btnAddStudent = findViewById(R.id.btnAddStudent);
        btnManageTeacher = findViewById(R.id.btnManageTeacher);
        btnManageStudent = findViewById(R.id.btnManageStudent);
        btnSendNotification = findViewById(R.id.btnSendNotification);

        btnAddTeacher.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, AddTeacherActivity.class);
            i.putExtra("mode", "teacher");
            startActivity(i);
        });

        btnAddStudent.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, AddTeacherActivity.class);
            i.putExtra("mode", "student");
            startActivity(i);
        });

        btnManageTeacher.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, ManageListActivity.class);
            i.putExtra("mode", "teacher");
            startActivity(i);
        });

        btnManageStudent.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, ManageListActivity.class);
            i.putExtra("mode", "student");
            startActivity(i);
        });
        btnSendNotification.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, SendNotificationActivity.class);
            startActivity(i);
        });
    }
}

