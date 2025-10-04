package com.example.myapplication;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import java.util.Calendar;

public class AddTeacherActivity extends AppCompatActivity {

    // ✨ ADDED NEW UI ELEMENTS
    private EditText etName, etPassword, etEmployeeId, etPhone, etDob;
    private TextView toggleTeacher, toggleStudent, tvEmployeeIdLabel;
    private Button btnAddUser;
    private DatabaseHelper db;
    private String mode = "teacher";

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
        etPhone = findViewById(R.id.etPhone);
        etDob = findViewById(R.id.etDob);
        toggleTeacher = findViewById(R.id.toggleTeacher);
        toggleStudent = findViewById(R.id.toggleStudent);
        tvEmployeeIdLabel = findViewById(R.id.tvEmployeeIdLabel);
        btnAddUser = findViewById(R.id.btnAddUser);

        toolbar.setNavigationOnClickListener(v -> finish());

        // Toggle logic
        toggleTeacher.setOnClickListener(v -> setMode("teacher"));
        toggleStudent.setOnClickListener(v -> setMode("student"));

        // ✨ DATE PICKER LOGIC
        etDob.setOnClickListener(v -> showDatePickerDialog());

        String initialMode = getIntent().getStringExtra("mode");
        if (initialMode != null) {
            setMode(initialMode);
        }

        btnAddUser.setOnClickListener(v -> saveUser());
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String selectedDate = (selectedMonth + 1) + "/" + selectedDay + "/" + selectedYear;
                    etDob.setText(selectedDate);
                }, year, month, day);
        datePickerDialog.show();
    }

    private void setMode(String newMode) {
        this.mode = newMode;
        if (mode.equals("teacher")) {
            toggleTeacher.setBackgroundResource(R.drawable.toggle_selected_background);
            toggleTeacher.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));
            toggleStudent.setBackgroundResource(R.drawable.toggle_unselected_background);
            toggleStudent.setTextColor(ContextCompat.getColor(this, R.color.textColorSecondary));
            tvEmployeeIdLabel.setText("Employee ID");
            etEmployeeId.setHint("Enter employee ID");
        } else { // Student mode
            toggleStudent.setBackgroundResource(R.drawable.toggle_selected_background);
            toggleStudent.setTextColor(ContextCompat.getColor(this, R.color.textColorPrimary));
            toggleTeacher.setBackgroundResource(R.drawable.toggle_unselected_background);
            toggleTeacher.setTextColor(ContextCompat.getColor(this, R.color.textColorSecondary));
            tvEmployeeIdLabel.setText("Student ID");
            etEmployeeId.setHint("Enter student ID");
        }
    }

    private void saveUser() {
        // ✨ GET TEXT FROM ALL FIELDS
        String name = etName.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String id = etEmployeeId.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String dob = etDob.getText().toString().trim();

        if (name.isEmpty() || password.isEmpty() || id.isEmpty() || phone.isEmpty() || dob.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isSuccess;
        String userType;

        if (mode.equals("teacher")) {
            // ✨ PASS ALL DATA TO THE DATABASE
            isSuccess = db.addTeacher(name, password, id, phone, dob);
            userType = "Teacher";
        } else {
            isSuccess = db.addStudent(name, password, id, phone, dob);
            userType = "Student";
        }

        if (isSuccess) {
            Toast.makeText(this, userType + " added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error: Username might already exist", Toast.LENGTH_SHORT).show();
        }
    }
}