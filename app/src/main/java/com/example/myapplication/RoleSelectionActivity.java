package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class RoleSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_selection);

        MaterialCardView cardAdmin = findViewById(R.id.cardAdmin);
        MaterialCardView cardTeacher = findViewById(R.id.cardTeacher);
        MaterialCardView cardStudent = findViewById(R.id.cardStudent);

        View.OnClickListener listener = v -> {
            // All cards will open the same LoginActivity
            startActivity(new Intent(RoleSelectionActivity.this, LoginActivity.class));
        };

        cardAdmin.setOnClickListener(listener);
        cardTeacher.setOnClickListener(listener);
        cardStudent.setOnClickListener(listener);
    }
}