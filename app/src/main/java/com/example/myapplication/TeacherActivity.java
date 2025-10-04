package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class TeacherActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        MaterialCardView cardMarkAttendance = findViewById(R.id.card_mark_attendance);
        // ✨ Find the new cards
        MaterialCardView cardViewAttendance = findViewById(R.id.card_view_attendance);
        MaterialCardView cardPublishAssignment = findViewById(R.id.card_publish_assignment);
        MaterialCardView cardSeeNotification = findViewById(R.id.card_see_notification);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            tvWelcome.setText("Welcome, " + username);
        }

        toolbar.setNavigationOnClickListener(v -> finish());

        cardMarkAttendance.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, MarkAttendanceOptionsActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        // ✨ Add OnClickListener for Publish Assignment
        cardPublishAssignment.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, PublishAssignmentActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        // ✨ Add OnClickListener for View Attendance (for now, a placeholder)
        cardViewAttendance.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, ViewTeacherAttendanceActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        cardSeeNotification.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, NotificationActivity.class);
            i.putExtra("userType", "teacher");
            startActivity(i);
        });

        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_attendance) {
                Intent i = new Intent(TeacherActivity.this, MarkAttendanceOptionsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            } else if (itemId == R.id.nav_notifications) {
                Intent i = new Intent(TeacherActivity.this, NotificationActivity.class);
                i.putExtra("userType", "teacher");
                startActivity(i);
            } else if (itemId == R.id.nav_schedule) {
                Intent i = new Intent(TeacherActivity.this, ScheduleActivity.class);
                i.putExtra("USER_ROLE", "teacher");
                i.putExtra("username", username);
                startActivity(i);
            } else if (itemId == R.id.nav_profile) {
                Toast.makeText(TeacherActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        bottomNavigation.setSelectedItemId(R.id.nav_profile);
    }
}