package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class MarkAttendanceOptionsActivity extends AppCompatActivity {

    private String teacherUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_attendance_options);

        // Get the teacher's username from the previous screen
        teacherUsername = getIntent().getStringExtra("username");

        // Find Views
        Toolbar toolbar = findViewById(R.id.toolbar);
        MaterialCardView cardMarkByQr = findViewById(R.id.card_mark_by_qr);
        MaterialCardView cardMarkByCode = findViewById(R.id.card_mark_by_code);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // Toolbar back button
        toolbar.setNavigationOnClickListener(v -> finish());

        // --- UPDATED CLICK LISTENERS ---
        // No more pop-up dialog. Generate QR code immediately.
        cardMarkByQr.setOnClickListener(v -> generateCode("qr"));

        // No more pop-up dialog. Generate special code immediately.
        cardMarkByCode.setOnClickListener(v -> generateCode("code"));

        // Set up Bottom Navigation
        bottomNavigation.setSelectedItemId(R.id.nav_attendance);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_attendance) {
                // Already here
            } else {
                Toast.makeText(this, item.getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }

    /**
     * This method now immediately starts the GenerateCodeActivity
     * with a default subject.
     * @param type "qr" or "code"
     */
    private void generateCode(String type) {
        // We'll use a default subject automatically
        String defaultSubject = "Class Attendance";

        Intent i = new Intent(MarkAttendanceOptionsActivity.this, GenerateCodeActivity.class);
        i.putExtra("teacher_username", teacherUsername);
        i.putExtra("subject", defaultSubject);
        i.putExtra("type", type);
        startActivity(i);
    }
}