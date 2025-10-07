package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class ScheduleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ClassAdapter adapter;
    private String userRole, username;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        userRole = getIntent().getStringExtra("USER_ROLE");
        username = getIntent().getStringExtra("username");
        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view_classes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab_add_class);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        if ("admin".equals(userRole)) {
            toolbar.setTitle("Master Schedule");
            fab.setVisibility(View.VISIBLE);
            bottomNav.inflateMenu(R.menu.admin_bottom_nav_menu);
            bottomNav.setSelectedItemId(R.id.nav_schedule);
        } else if ("teacher".equals(userRole)) {
            toolbar.setTitle("Professor Schedule");
            fab.setVisibility(View.GONE);
            bottomNav.inflateMenu(R.menu.teacher_bottom_nav_menu);
            bottomNav.setSelectedItemId(R.id.nav_schedule);
        } else { // Student
            toolbar.setTitle("User Schedule");
            fab.setVisibility(View.GONE);
            bottomNav.inflateMenu(R.menu.student_bottom_nav_menu);
        }

        fab.setOnClickListener(v -> startActivity(new Intent(this, AddClassActivity.class)));

        bottomNav.setOnItemSelectedListener(item -> {
            // Your navigation logic here
            return true;
        });

        loadClasses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadClasses();
    }

    private void loadClasses() {
        List<ClassItem> classList;
        if ("admin".equals(userRole) || "student".equals(userRole)) {
            classList = db.getAllClasses();
        } else {
            classList = db.getClassesForTeacher(username);
        }
        adapter = new ClassAdapter(classList);
        recyclerView.setAdapter(adapter);
    }
}