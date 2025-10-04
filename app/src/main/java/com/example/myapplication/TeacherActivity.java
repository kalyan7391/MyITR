package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class TeacherActivity extends AppCompatActivity {
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        // Find Views from the new layout
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        MaterialCardView cardMarkAttendance = findViewById(R.id.card_mark_attendance);
        MaterialCardView cardGiveNotification = findViewById(R.id.card_give_notification);
        MaterialCardView cardSeeNotification = findViewById(R.id.card_see_notification);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // Get username from Intent and set welcome message
        username = getIntent().getStringExtra("username");
        if (username != null && !username.isEmpty()) {
            tvWelcome.setText("Welcome, " + username);
        }

        // Set Toolbar navigation
        toolbar.setNavigationOnClickListener(v -> finish());

        // --- Make Cards Functional ---
        cardMarkAttendance.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, MarkAttendanceOptionsActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        cardSeeNotification.setOnClickListener(v -> {
            Intent i = new Intent(TeacherActivity.this, NotificationActivity.class);
            i.putExtra("userType", "teacher"); // To see notifications from admin
            startActivity(i);
        });

        cardGiveNotification.setOnClickListener(v -> {
            Toast.makeText(this, "Opening notification sender...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(TeacherActivity.this, SendNotificationActivity.class));
        });

        // --- Make Bottom Navigation Functional ---
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_attendance) {
                // You can open the attendance screen directly if you want
                Intent i = new Intent(TeacherActivity.this, MarkAttendanceOptionsActivity.class);
                i.putExtra("username", username);
                startActivity(i);
            } else if (itemId == R.id.nav_notifications) {
                Intent i = new Intent(TeacherActivity.this, NotificationActivity.class);
                i.putExtra("userType", "teacher");
                startActivity(i);
            } else if (itemId == R.id.nav_schedule) {
                // ✨ THIS IS THE CODE THAT OPENS THE SCHEDULE SCREEN ✨
                Intent i = new Intent(TeacherActivity.this, ScheduleActivity.class);
                i.putExtra("USER_ROLE", "teacher");
                i.putExtra("username", username); // Pass the teacher's username
                startActivity(i);
            } else if (itemId == R.id.nav_profile) {
                Toast.makeText(TeacherActivity.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        // Set a default selected item for the bottom navigation
        bottomNavigation.setSelectedItemId(R.id.nav_profile);
    }
}