package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
// ✨ FIX: Import MaterialToolbar
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class MarkAttendanceOptionsActivity extends AppCompatActivity {

    private String teacherUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_attendance_options);

        teacherUsername = getIntent().getStringExtra("username");

        // ✨ FIX: Use MaterialToolbar class
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        MaterialCardView cardMarkByQr = findViewById(R.id.card_mark_by_qr);
        MaterialCardView cardMarkByCode = findViewById(R.id.card_mark_by_code);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        toolbar.setNavigationOnClickListener(v -> finish());

        cardMarkByQr.setOnClickListener(v -> generateCode("qr"));
        cardMarkByCode.setOnClickListener(v -> generateCode("code"));

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

    private void generateCode(String type) {
        String defaultSubject = "Class Attendance";
        Intent i = new Intent(MarkAttendanceOptionsActivity.this, GenerateCodeActivity.class);
        i.putExtra("teacher_username", teacherUsername);
        i.putExtra("subject", defaultSubject);
        i.putExtra("type", type);
        startActivity(i);
    }
}