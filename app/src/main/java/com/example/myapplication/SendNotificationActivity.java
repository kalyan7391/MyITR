// app/src/main/java/com/example/myapplication/SendNotificationActivity.java
package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SendNotificationActivity extends AppCompatActivity {
    EditText etSubject, etMessage;
    Spinner spUserType;
    Button btnSend;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        db = new DatabaseHelper(this);
        etSubject = findViewById(R.id.etSubject);
        etMessage = findViewById(R.id.etMessage);
        spUserType = findViewById(R.id.spUserType);
        btnSend = findViewById(R.id.btnSend);

        // Populate spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spUserType.setAdapter(adapter);

        btnSend.setOnClickListener(v -> {
            String subject = etSubject.getText().toString();
            String message = etMessage.getText().toString();
            String userType = spUserType.getSelectedItem().toString().toLowerCase();

            if (subject.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (db.addNotification(message, userType, subject)) {
                Toast.makeText(this, "Notification sent", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to send notification", Toast.LENGTH_SHORT).show();
            }
        });
    }
}