package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import java.util.List; // Import List

public class FeedbackActivity extends AppCompatActivity {

    private Spinner spinnerSubject;
    // ✨ DatabaseHelper instance
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        // ✨ Initialize the database helper
        db = new DatabaseHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        spinnerSubject = findViewById(R.id.spinner_subject);
        Button btnSubmit = findViewById(R.id.btn_submit_feedback);

        // ✨ FIX: Populate Spinner with subjects from the database
        loadSubjectsIntoSpinner();

        btnSubmit.setOnClickListener(v -> {
            // In a real app, you would save the feedback to the database.
            Toast.makeText(this, "Feedback submitted!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void loadSubjectsIntoSpinner() {
        // Fetch the list of subjects from the database
        List<String> subjects = db.getDistinctSubjects();

        // Create an adapter from the dynamic list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSubject.setAdapter(adapter);
    }
}