package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
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

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        MaterialCardView cardMarkByQr = findViewById(R.id.card_mark_by_qr);
        MaterialCardView cardMarkByCode = findViewById(R.id.card_mark_by_code);
        MaterialCardView cardMarkAbsentees = findViewById(R.id.card_mark_absentees); // New CardView
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        toolbar.setNavigationOnClickListener(v -> finish());

        cardMarkByQr.setOnClickListener(v -> openSubjectSelector("qr"));
        cardMarkByCode.setOnClickListener(v -> openSubjectSelector("code"));
        cardMarkAbsentees.setOnClickListener(v -> openSubjectSelector("absent")); // Handle click

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

    private void openSubjectSelector(String type) {
        Intent i = new Intent(MarkAttendanceOptionsActivity.this, SelectSubjectActivity.class);
        i.putExtra("username", teacherUsername);
        i.putExtra("type", type);
        startActivity(i);
    }
}