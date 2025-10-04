package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MarkAttendanceOptionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_attendance_options);

        EditText etSubject = findViewById(R.id.etSubject);
        Button btnGenerateQRCode = findViewById(R.id.btnGenerateQRCode);
        Button btnGenerateCode = findViewById(R.id.btnGenerateCode);

        String teacherUsername = getIntent().getStringExtra("username");

        btnGenerateQRCode.setOnClickListener(v -> {
            String subject = etSubject.getText().toString().trim();
            if (subject.isEmpty()) {
                Toast.makeText(this, "Please enter a subject", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(MarkAttendanceOptionsActivity.this, GenerateCodeActivity.class);
                i.putExtra("teacher_username", teacherUsername);
                i.putExtra("subject", subject);
                i.putExtra("type", "qr");
                startActivity(i);
            }
        });

        btnGenerateCode.setOnClickListener(v -> {
            String subject = etSubject.getText().toString().trim();
            if (subject.isEmpty()) {
                Toast.makeText(this, "Please enter a subject", Toast.LENGTH_SHORT).show();
            } else {
                Intent i = new Intent(MarkAttendanceOptionsActivity.this, GenerateCodeActivity.class);
                i.putExtra("teacher_username", teacherUsername);
                i.putExtra("subject", subject);
                i.putExtra("type", "code");
                startActivity(i);
            }
        });
    }
}