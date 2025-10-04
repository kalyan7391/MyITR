package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class StudentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // You would create a layout file named 'activity_student.xml'
        // For now, let's assume it has a TextView with id 'tvWelcome'
        setContentView(R.layout.activity_student);
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        Button btnViewNotifications = findViewById(R.id.btnViewNotifications);
        String username = getIntent().getStringExtra("username");
        tvWelcome.setText("Welcome, " + username);
        btnViewNotifications.setOnClickListener(v -> {
            Intent i = new Intent(StudentActivity.this, NotificationActivity.class);
            i.putExtra("userType", "student");
            startActivity(i);
        });
    }
}