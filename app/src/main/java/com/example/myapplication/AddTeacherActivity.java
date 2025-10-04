package com.example.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class AddTeacherActivity extends AppCompatActivity {

    private EditText etName, etPassword, etEmployeeId;
    private TextView toggleTeacher, toggleStudent, tvEmployeeIdLabel;
    private Button btnAddUser;
    private DatabaseHelper db;
    private String mode = "teacher"; // Default mode

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);

        db = new DatabaseHelper(this);

        // Initialize views
        Toolbar toolbar = findViewById(R.id.toolbar);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etEmployeeId = findViewById(R.id.etEmployeeId);
        toggleTeacher = findViewById(R.id.toggleTeacher);
        toggleStudent = findViewById(R.id.toggleStudent);
        tvEmployeeIdLabel = findViewById(R.id.tvEmployeeIdLabel);
        btnAddUser = findViewById(R.id.btnAddUser);

        // Toolbar back button
        toolbar.setNavigationOnClickListener(v -> finish());

        // Toggle logic
        toggleTeacher.setOnClickListener(v -> setMode("teacher"));
        toggleStudent.setOnClickListener(v -> setMode("student"));

        // Set initial mode from intent if available
        String initialMode = getIntent().getStringExtra("mode");
        if (initialMode != null) {
            setMode(initialMode);
        }

        // Save button logic
        btnAddUser.setOnClickListener(v -> saveUser());
    }

    private void setMode(String newMode) {
        this.mode = newMode;
        if (mode.equals("teacher")) {
            // Set Teacher as selected
            toggleTeacher.setBackgroundResource(R.drawable.toggle_selected_background);
            toggleTeacher.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));

            toggleStudent.setBackgroundResource(R.drawable.toggle_unselected_background);
            toggleStudent.setTextColor(ContextCompat.getColor(this, R.color.textColorSecondary));

            // Update labels for teacher
            tvEmployeeIdLabel.setText("Employee ID");
            etEmployeeId.setHint("Enter employee ID");

        } else { // Student mode
            // Set Student as selected
            toggleStudent.setBackgroundResource(R.drawable.toggle_selected_background);
            toggleStudent.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));

            toggleTeacher.setBackgroundResource(R.drawable.toggle_unselected_background);
            toggleTeacher.setTextColor(ContextCompat.getColor(this, R.color.textColorSecondary));

            // Update labels for student
            tvEmployeeIdLabel.setText("Student ID");
            etEmployeeId.setHint("Enter student ID");
        }
    }

    private void saveUser() {
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter a name and password", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isSuccess;
        String userType;

        if (mode.equals("teacher")) {
            isSuccess = db.addTeacher(name, password);
            userType = "Teacher";
        } else {
            isSuccess = db.addStudent(name, password);
            userType = "Student";
        }

        if (isSuccess) {
            Toast.makeText(this, userType + " added successfully", Toast.LENGTH_SHORT).show();
            finish(); // Go back to the previous screen
        } else {
            Toast.makeText(this, "Error: Username might already exist", Toast.LENGTH_SHORT).show();
        }
    }
}