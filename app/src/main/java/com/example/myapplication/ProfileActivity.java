package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String studentUsername;
    private String imageUriString; // Will store the URI as a string

    private CircleImageView profileImageView;
    private TextView tvName, tvId, tvPhone, tvDob;
    private EditText etDivision, etAadhaar, etParentPhone;
    private Button btnUpload, btnSave;

    // ✨ FIX: Updated the launcher to correctly handle persistent permissions
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null && result.getData().getData() != null) {
                    Uri imageUri = result.getData().getData();

                    // Take persistent permission to read this URI
                    final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                    getContentResolver().takePersistableUriPermission(imageUri, takeFlags);

                    profileImageView.setImageURI(imageUri);
                    imageUriString = imageUri.toString();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);
        studentUsername = getIntent().getStringExtra("username");

        Toolbar toolbar = findViewById(R.id.toolbar);
        profileImageView = findViewById(R.id.iv_profile_image);
        btnUpload = findViewById(R.id.btn_upload_photo);
        btnSave = findViewById(R.id.btn_save_profile);
        tvName = findViewById(R.id.tv_profile_name);
        tvId = findViewById(R.id.tv_profile_id);
        tvPhone = findViewById(R.id.tv_profile_phone);
        tvDob = findViewById(R.id.tv_profile_dob);
        etDivision = findViewById(R.id.et_profile_division);
        etAadhaar = findViewById(R.id.et_profile_aadhaar);
        etParentPhone = findViewById(R.id.et_profile_parent_phone);

        toolbar.setNavigationOnClickListener(v -> finish());

        loadStudentData();

        btnUpload.setOnClickListener(v -> {
            // ✨ FIX: Use ACTION_OPEN_DOCUMENT to allow for persistent permissions
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            pickImageLauncher.launch(intent);
        });

        btnSave.setOnClickListener(v -> saveStudentData());
    }

    private void loadStudentData() {
        if (studentUsername != null) {
            User student = db.getStudentDetails(studentUsername);
            if (student != null) {
                tvName.setText(student.getName());
                tvId.setText(student.getEmployeeId());
                tvPhone.setText(student.getPhone());
                tvDob.setText(student.getDob());
                etDivision.setText(student.getDivision());
                etAadhaar.setText(student.getAadhaarNumber());
                etParentPhone.setText(student.getParentsPhone());

                if (student.getProfileImagePath() != null && !student.getProfileImagePath().isEmpty()) {
                    imageUriString = student.getProfileImagePath();
                    // This will now work across app restarts due to the persistent permission
                    profileImageView.setImageURI(Uri.parse(imageUriString));
                }
            }
        }
    }

    private void saveStudentData() {
        String division = etDivision.getText().toString().trim();
        String aadhaar = etAadhaar.getText().toString().trim();
        String parentPhone = etParentPhone.getText().toString().trim();

        if (TextUtils.isEmpty(division) || TextUtils.isEmpty(aadhaar) || TextUtils.isEmpty(parentPhone)) {
            Toast.makeText(this, "Please fill all editable fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isSuccess = db.updateStudentDetails(studentUsername, division, aadhaar, parentPhone, imageUriString);

        if (isSuccess) {
            Toast.makeText(this, "Profile updated successfully!", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show();
        }
    }
}