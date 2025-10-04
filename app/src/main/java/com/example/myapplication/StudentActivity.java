package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StudentActivity extends AppCompatActivity {

    private String username;
    private DatabaseHelper db;

    private final ActivityResultLauncher<ScanOptions> barcodeLauncher = registerForActivityResult(new ScanContract(),
            result -> {
                if(result.getContents() == null) {
                    Toast.makeText(StudentActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    String[] data = result.getContents().split(":");
                    if (data.length == 2) {
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
        TextView tvWelcome = findViewById(R.id.tvWelcome);
        Button btnViewNotifications = findViewById(R.id.btnViewNotifications);
        Button btnViewAttendance = findViewById(R.id.btnViewAttendance);
        Button btnScanQRCode = findViewById(R.id.btnScanQRCode);
        Button btnEnterCode = findViewById(R.id.btnEnterCode);

        username = getIntent().getStringExtra("username");
        tvWelcome.setText("Welcome, " + username);

        btnViewNotifications.setOnClickListener(v -> {
            Intent i = new Intent(StudentActivity.this, NotificationActivity.class);
            i.putExtra("userType", "student");
            startActivity(i);
        });

        btnViewAttendance.setOnClickListener(v -> {
            Intent i = new Intent(StudentActivity.this, AttendanceActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });

        btnScanQRCode.setOnClickListener(v -> {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Scan QR Code");
            options.setBeepEnabled(true);
            options.setOrientationLocked(false);
            barcodeLauncher.launch(options);
        });

        btnEnterCode.setOnClickListener(v -> {
            Intent i = new Intent(StudentActivity.this, EnterCodeActivity.class);
            i.putExtra("username", username);
            startActivity(i);
        });
    }
}