package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class AdminActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // This dynamically sets up the text and icons for each card
        setupCard(R.id.item_add_teacher, R.drawable.ic_person_add, "Add Teacher", "Add a new teacher");
        setupCard(R.id.item_manage_teachers, R.drawable.ic_manage_accounts, "Manage Teachers", "Edit teacher profiles");
        setupCard(R.id.item_add_student, R.drawable.ic_person_add, "Add Student", "Enroll a new student");
        setupCard(R.id.item_manage_students, R.drawable.ic_manage_accounts, "Manage Students", "Manage student records");
        setupCard(R.id.item_send_notification, R.drawable.ic_campaign, "Send Notification", "Send updates and announcements");

        // Find the main CardView containers from the layout
        MaterialCardView cardAddTeacher = findViewById(R.id.card_add_teacher);
        MaterialCardView cardManageTeachers = findViewById(R.id.card_manage_teachers);
        MaterialCardView cardAddStudent = findViewById(R.id.card_add_student);
        MaterialCardView cardManageStudents = findViewById(R.id.card_manage_students);
        MaterialCardView cardSendNotification = findViewById(R.id.card_send_notification);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // --- Make the Cards Functional ---
        cardAddTeacher.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, AddTeacherActivity.class);
            i.putExtra("mode", "teacher");
            startActivity(i);
        });

        cardAddStudent.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, AddTeacherActivity.class);
            i.putExtra("mode", "student");
            startActivity(i);
        });

        cardManageTeachers.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, ManageListActivity.class);
            i.putExtra("mode", "teacher");
            startActivity(i);
        });

        cardManageStudents.setOnClickListener(v -> {
            Intent i = new Intent(AdminActivity.this, ManageListActivity.class);
            i.putExtra("mode", "student");
            startActivity(i);
        });

        cardSendNotification.setOnClickListener(v -> {
            startActivity(new Intent(AdminActivity.this, SendNotificationActivity.class));
        });

        // --- Make the Bottom Navigation Functional ---
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                Toast.makeText(AdminActivity.this, "Dashboard clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_users) {
                Toast.makeText(AdminActivity.this, "Users clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_notifications) {
                Toast.makeText(AdminActivity.this, "Notifications clicked", Toast.LENGTH_SHORT).show();
            } else if (itemId == R.id.nav_settings) {
                Toast.makeText(AdminActivity.this, "Settings clicked", Toast.LENGTH_SHORT).show();
            }
            // Return true to display the item as the selected item
            return true;
        });
    }

    /**
     * A helper method to set the icon, title, and subtitle for a reusable card item.
     * @param viewId The ID of the <include> tag in the main layout.
     * @param iconResId The drawable resource ID for the icon.
     * @param title The string for the main title.
     * @param subtitle The string for the subtitle.
     */
    private void setupCard(int viewId, int iconResId, String title, String subtitle) {
        View cardItem = findViewById(viewId);
        ImageView icon = cardItem.findViewById(R.id.card_icon);
        TextView titleView = cardItem.findViewById(R.id.card_title);
        TextView subtitleView = cardItem.findViewById(R.id.card_subtitle);

        icon.setImageResource(iconResId);
        titleView.setText(title);
        subtitleView.setText(subtitle);
    }
}