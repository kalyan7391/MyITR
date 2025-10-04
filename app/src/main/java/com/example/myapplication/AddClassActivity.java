package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.List;
import java.util.ArrayList;

public class AddClassActivity extends AppCompatActivity {

    private EditText etClassName, etClassTime;
    private Spinner spinnerTeachers; // ✨ ADDED SPINNER
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        db = new DatabaseHelper(this);
        etClassName = findViewById(R.id.et_class_name);
        etClassTime = findViewById(R.id.et_class_time);
        spinnerTeachers = findViewById(R.id.spinner_teachers); // ✨ INITIALIZE SPINNER
        Button btnSave = findViewById(R.id.btn_save_class);
        Toolbar toolbar = findViewById(R.id.toolbar);

        toolbar.setNavigationOnClickListener(v -> finish());

        // ✨ POPULATE THE SPINNER WITH TEACHERS
        loadTeachersIntoSpinner();

        btnSave.setOnClickListener(v -> saveClass());
    }

    private void loadTeachersIntoSpinner() {
        // This method fetches the names of all teachers
        List<User> teachers = db.getAllTeachers();
        // We need a list of strings for the adapter
        List<String> teacherNames = new ArrayList<>();
        for (User teacher : teachers) {
            teacherNames.add(teacher.getName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, teacherNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTeachers.setAdapter(adapter);
    }

    private void saveClass() {
        String name = etClassName.getText().toString().trim();
        String time = etClassTime.getText().toString().trim();

        // ✨ GET THE SELECTED TEACHER FROM THE SPINNER
        String selectedTeacher = spinnerTeachers.getSelectedItem().toString();

        if (name.isEmpty() || time.isEmpty() || selectedTeacher.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.addClass(name, time, selectedTeacher)) {
            Toast.makeText(this, "Class added for " + selectedTeacher, Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add class", Toast.LENGTH_SHORT).show();
        }
    }
}