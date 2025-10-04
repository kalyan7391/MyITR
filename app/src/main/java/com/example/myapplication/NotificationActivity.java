// app/src/main/java/com/example/myapplication/NotificationActivity.java
package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    ListView lvNotifications;
    DatabaseHelper db;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        db = new DatabaseHelper(this);
        lvNotifications = findViewById(R.id.lvNotifications);
        userType = getIntent().getStringExtra("userType");

        List<String> notifications = db.getNotifications(userType);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, notifications);
        lvNotifications.setAdapter(adapter);
    }
}