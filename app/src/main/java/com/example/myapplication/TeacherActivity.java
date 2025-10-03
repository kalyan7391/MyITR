package com.example.myapplication;



import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TeacherActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // You would create a layout file named 'activity_teacher.xml'
        // For now, let's assume it has a TextView with id 'tvWelcome'
        setContentView(R.layout.activity_teacher);
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        String username = getIntent().getStringExtra("username");
        tvWelcome.setText("Welcome, " + username);
    }
}