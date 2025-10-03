package com.example.myapplication;



import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddTeacherActivity extends AppCompatActivity {
    EditText etNewUser, etNewPass;
    Button btnSave;
    DatabaseHelper db;
    String mode; // "teacher" or "student"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        db = new DatabaseHelper(this);
        etNewUser = findViewById(R.id.etNewUsername);
        etNewPass = findViewById(R.id.etNewPassword);
        btnSave = findViewById(R.id.btnSave);
        mode = getIntent().getStringExtra("mode");

        if (mode == null) mode = "teacher";

        btnSave.setOnClickListener(v -> {
            String u = etNewUser.getText().toString().trim();
            String p = etNewPass.getText().toString().trim();
            if (TextUtils.isEmpty(u) || TextUtils.isEmpty(p)) {
                Toast.makeText(this, "Enter username & password", Toast.LENGTH_SHORT).show();
                return;
            }
            boolean ok;
            if (mode.equals("teacher")) ok = db.addTeacher(u, p);
            else ok = db.addStudent(u, p);

            if (ok) {
                Toast.makeText(this, (mode.equals("teacher") ? "Teacher" : "Student") + " added", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Error: username might already exist", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

