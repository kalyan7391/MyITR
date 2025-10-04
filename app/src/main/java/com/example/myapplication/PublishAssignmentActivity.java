package com.example.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class PublishAssignmentActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String teacherUsername;
    private String fileUriString;

    private Spinner spinnerSubjects;
    private EditText etTitle, etDescription, etDueDate;
    private TextView tvAttachedFile;
    private Button btnAttachFile, btnPublish;

    private final ActivityResultLauncher<Intent> pickFileLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                    Uri fileUri = result.getData().getData();
                    // ✨ FIX: Wrap the permission request in a try-catch block to prevent crashes
                    try {
                        final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                        getContentResolver().takePersistableUriPermission(fileUri, takeFlags);

                        fileUriString = fileUri.toString();
                        tvAttachedFile.setText("File attached: " + fileUri.getLastPathSegment());

                    } catch (SecurityException e) {
                        Log.e("PublishAssignment", "Failed to take permission", e);
                        Toast.makeText(this, "Failed to get permission for file.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_assignment);

        db = new DatabaseHelper(this);
        teacherUsername = getIntent().getStringExtra("username");

        Toolbar toolbar = findViewById(R.id.toolbar);
        spinnerSubjects = findViewById(R.id.spinner_subjects);
        etTitle = findViewById(R.id.et_assignment_title);
        etDescription = findViewById(R.id.et_assignment_description);
        etDueDate = findViewById(R.id.et_due_date);
        tvAttachedFile = findViewById(R.id.tv_attached_file_name);
        btnAttachFile = findViewById(R.id.btn_attach_file);
        btnPublish = findViewById(R.id.btn_publish);

        toolbar.setNavigationOnClickListener(v -> finish());

        loadSubjectsIntoSpinner();

        etDueDate.setOnClickListener(v -> showDatePickerDialog());
        btnAttachFile.setOnClickListener(v -> openFilePicker());
        btnPublish.setOnClickListener(v -> publishAssignment());
    }

    private void loadSubjectsIntoSpinner() {
        List<ClassItem> classes = db.getClassesForTeacher(teacherUsername);
        List<String> subjectNames = classes.stream()
                .map(ClassItem::getName)
                .distinct()
                .collect(Collectors.toList());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, subjectNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSubjects.setAdapter(adapter);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, day) -> {
                    String selectedDate = (month + 1) + "/" + day + "/" + year;
                    etDueDate.setText(selectedDate);
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void openFilePicker() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        pickFileLauncher.launch(intent);
    }

    private void publishAssignment() {
        String subject = spinnerSubjects.getSelectedItem().toString();
        String title = etTitle.getText().toString().trim();
        String dueDate = etDueDate.getText().toString().trim();

        if (TextUtils.isEmpty(subject) || TextUtils.isEmpty(title) || TextUtils.isEmpty(dueDate)) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fileUriString == null) {
            Toast.makeText(this, "Please attach a file", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isSuccess = db.addAssignment(subject, title, dueDate, teacherUsername, fileUriString);

        if (isSuccess) {
            Toast.makeText(this, "Assignment published successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to publish assignment", Toast.LENGTH_SHORT).show();
        }
    }
}