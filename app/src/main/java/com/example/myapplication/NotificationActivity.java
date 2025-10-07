package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private NoticeAdapter adapter;
    private DatabaseHelper db;
    private String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        db = new DatabaseHelper(this);
        userType = getIntent().getStringExtra("userType");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Announcements");
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view_notifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Notice> notices = db.getNotifications(userType);

        adapter = new NoticeAdapter(notices);
        recyclerView.setAdapter(adapter);
    }
}