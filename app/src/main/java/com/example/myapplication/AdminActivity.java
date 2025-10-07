package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin); // make sure this matches your XML file name

        // Setup all the admin dashboard cards
        configureAdminCard(
                R.id.card_add_teacher,
                R.drawable.ic_person_add,
                "Add Teacher",
                "Add a new teacher"
        );

        configureAdminCard(
                R.id.card_manage_teachers,
                R.drawable.ic_group,
                "Manage Teachers",
                "Edit teacher profiles"
        );

        configureAdminCard(
                R.id.card_add_student,
                R.drawable.ic_person_add_alt,
                "Add Student",
                "Enroll a new student"
        );

        configureAdminCard(
                R.id.card_manage_students,
                R.drawable.ic_group_alt,
                "Manage Students",
                "Manage student records"
        );

        configureAdminCard(
                R.id.card_send_notification,
                R.drawable.ic_notifications,
                "Send Notification",
                "Send updates and announcements"
        );
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
                Toast.makeText(AdminActivity.this, title + " clicked", Toast.LENGTH_SHORT).show();

                // TODO: Start new activities if needed
                /*
                if (title.equals("Add Teacher")) {
                    startActivity(new Intent(this, AddTeacherActivity.class));
                }
                */
            });
        }
    }
}
