package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.widget.TextView;
import java.util.Locale;

public class StudentActivity extends AppCompatActivity {

    private String username;
    private DatabaseHelper db;

    // QR Code scanner launcher
    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(StudentActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String[] data = result.getContents().split(":");
                    if (data.length >= 2) { // Use >= 2 to be safe with the new timestamp
                        String teacherUsername = data[0];
                        String subject = data[1];
                        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        db.addAttendance(username, teacherUsername, subject, date, "Present");
                        Toast.makeText(this, "Attendance marked for " + subject, Toast.LENGTH_SHORT).show();
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        db = new DatabaseHelper(this);
        username = getIntent().getStringExtra("username");

        // --- Find all UI elements ---
        TextView tvStudentName = findViewById(R.id.tv_student_name); // ✨ Find the new TextView


        Button btnScanQRCode = findViewById(R.id.btnScanQRCode);
        Button btnEnterCode = findViewById(R.id.btnEnterCode);
        CardView cardViewAttendance = findViewById(R.id.card_view_attendance);
        CardView cardSyllabus = findViewById(R.id.card_my_syllabus);
        CardView cardNotice = findViewById(R.id.card_notice);
        CardView cardFeedback = findViewById(R.id.card_feedback);
        CardView cardAssignments = findViewById(R.id.card_see_assignments);
        CardView cardScheduler = findViewById(R.id.card_scheduler);
        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);

        // --- Set OnClick Listeners ---
        if (username != null && !username.isEmpty()) {
            tvStudentName.setText("Welcome, " + username);
        }
        // Attendance Buttons
        btnScanQRCode.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan QR Code for Attendance");
            options.setBeepEnabled(true);
            barcodeLauncher.launch(options);
        });

        btnEnterCode.setOnClickListener(v -> {
            Intent i = new Intent(StudentActivity.this, EnterCodeActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        // Feature Cards
        cardViewAttendance.setOnClickListener(v -> {
            Intent i = new Intent(StudentActivity.this, AttendanceActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        cardNotice.setOnClickListener(v -> {
            Intent i = new Intent(StudentActivity.this, NotificationActivity.class);
            i.putExtra("userType", "student");
            startActivity(i);
        });

        cardScheduler.setOnClickListener(v -> {
            // Students can also view the schedule
            Intent i = new Intent(StudentActivity.this, ScheduleActivity.class);
            i.putExtra("USER_ROLE", "student");
            startActivity(i);
        });

        // Placeholder Activities
        cardSyllabus.setOnClickListener(v -> startActivity(new Intent(this, SyllabusActivity.class)));
        cardFeedback.setOnClickListener(v -> startActivity(new Intent(this, FeedbackActivity.class)));
        cardAssignments.setOnClickListener(v -> startActivity(new Intent(this, AssignmentsActivity.class)));

        // --- Bottom Navigation ---
        bottomNavigation.setSelectedItemId(R.id.nav_home);
        bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_home) {
                // Already here
            } else {
                Toast.makeText(this, item.getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
            }
            return true;
        });
    }
}