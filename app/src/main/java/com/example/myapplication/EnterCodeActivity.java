package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EnterCodeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        EditText etTeacherUsername = findViewById(R.id.etTeacherUsername);
        EditText etSubject = findViewById(R.id.etSubject);
        EditText etCode = findViewById(R.id.etCode);
        Button btnSubmit = findViewById(R.id.btnSubmit);

        String studentUsername = getIntent().getStringExtra("username");
        DatabaseHelper db = new DatabaseHelper(this);

        btnSubmit.setOnClickListener(v -> {
            String teacherUsername = etTeacherUsername.getText().toString().trim();
            String subject = etSubject.getText().toString().trim();
            String code = etCode.getText().toString().trim();

            if (teacherUsername.isEmpty() || subject.isEmpty() || code.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                // In a real application, you would validate the code.
                String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                // ✨ FIXED: Correctly calling the addAttendance method
                db.addAttendance(studentUsername, teacherUsername, subject, date, "Present");
                Toast.makeText(this, "Attendance marked for " + subject, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}