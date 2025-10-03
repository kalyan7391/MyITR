package com.example.myapplication;




import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    EditText etUser, etPass;
    Button btnLogin;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DatabaseHelper(this);

        etUser = findViewById(R.id.etUsername);
        etPass = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = etUser.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pass)) {
                Toast.makeText(this, "Enter username & password", Toast.LENGTH_SHORT).show();
                return;
            }

            // Admin hard-coded credentials
            if (user.equals("admin") && pass.equals("admin")) {
                startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                finish();
                return;
            }

            // Check teacher
            if (db.checkTeacherCredentials(user, pass)) {
                Intent i = new Intent(LoginActivity.this, TeacherActivity.class);
                i.putExtra("username", user);
                startActivity(i);
                finish();
                return;
            }

            // Check student
            if (db.checkStudentCredentials(user, pass)) {
                Intent i = new Intent(LoginActivity.this, StudentActivity.class);
                i.putExtra("username", user);
                startActivity(i);
                finish();
                return;
            }

            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        });
    }
}
