package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // make sure this matches your XML file name

        // Setup all the admin dashboard cards
        configureAdminCard(
                R.id.card_add_teacher,
                R.drawable.ic_person_add,
                "Add Professor",
                "Add a new professor"
        );

        configureAdminCard(
                R.id.card_manage_teachers,
                R.drawable.ic_group,
                "Manage Professors",
                "Edit professor profiles"
        );

        configureAdminCard(
                R.id.card_add_student,
                R.drawable.ic_person_add,
                "Add User",
                "Enroll a new user"
        );

        configureAdminCard(
                R.id.card_manage_students,
                R.drawable.ic_group,
                "Manage Users",
                "Manage user records"
        );

        configureAdminCard(
                R.id.card_send_notification,
                R.drawable.ic_notifications,
                "Send Announcement",
                "Send updates and announcements"
        );

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_schedule) {
                Intent intent = new Intent(AdminActivity.this, ScheduleActivity.class);
                intent.putExtra("USER_ROLE", "admin");
                startActivity(intent);
                return true;
            }
            // Add other navigation items here if needed
            return false;
        });
    }

    private void configureAdminCard(int layoutId, int iconRes, String title, String subtitle) {
        View card = findViewById(layoutId);

        if (card != null) {
            ImageView icon = card.findViewById(R.id.card_icon);
            TextView titleText = card.findViewById(R.id.card_title);
            TextView subtitleText = card.findViewById(R.id.card_subtitle);

            // Set the icon, title, and subtitle
            icon.setImageResource(iconRes);
            titleText.setText(title);
            subtitleText.setText(subtitle);

            // Handle click action
            card.setOnClickListener(v -> {
                Intent intent = null;
                switch (title) {
                    case "Add Professor":
                        intent = new Intent(AdminActivity.this, AddTeacherActivity.class);
                        intent.putExtra("mode", "teacher");
                        break;
                    case "Manage Professors":
                        intent = new Intent(AdminActivity.this, ManageListActivity.class);
                        intent.putExtra("mode", "teacher");
                        break;
                    case "Add User":
                        intent = new Intent(AdminActivity.this, AddTeacherActivity.class);
                        intent.putExtra("mode", "student");
                        break;
                    case "Manage Users":
                        intent = new Intent(AdminActivity.this, ManageListActivity.class);
                        intent.putExtra("mode", "student");
                        break;
                    case "Send Announcement":
                        intent = new Intent(AdminActivity.this, SendNotificationActivity.class);
                        break;
                }
                if (intent != null) {
                    startActivity(intent);
                } else {
                    Toast.makeText(AdminActivity.this, title + " clicked", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}