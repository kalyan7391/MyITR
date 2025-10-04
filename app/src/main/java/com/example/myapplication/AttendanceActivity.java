package com.example.myapplication;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.Map;

public class AttendanceActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String studentUsername;
    private TextView tvPercentage, tvStatus;
    private CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        db = new DatabaseHelper(this);
        studentUsername = getIntent().getStringExtra("username");

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        tvPercentage = findViewById(R.id.tvPercentage);
        tvStatus = findViewById(R.id.tvStatus);
        calendarView = findViewById(R.id.calendarView);

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setSelectedItemId(R.id.nav_attendance);

        loadAttendanceData();
    }

    private void loadAttendanceData() {
        Map<String, String> summary = db.getAttendanceSummary(studentUsername);
        tvPercentage.setText(summary.get("percentage"));
        tvStatus.setText(summary.get("status"));

        if ("Good".equals(summary.get("status"))) {
            tvStatus.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            tvStatus.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }

        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            // Logic to show attendance for a specific day could be added here.
        });
    }
}