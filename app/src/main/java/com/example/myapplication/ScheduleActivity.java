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
        username = getIntent().getStringExtra("username"); // Passed from TeacherActivity


        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view_classes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FloatingActionButton fab = findViewById(R.id.fab_add_class);
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        // Check user role passed from previous activity
        String userRole = getIntent().getStringExtra("USER_ROLE");

        // ✨ --- FIXED CODE --- ✨
        // The correct way to change the menu is to inflate a new one.
        if ("admin".equals(userRole)) {
            fab.setVisibility(View.VISIBLE);
            bottomNav.inflateMenu(R.menu.admin_bottom_nav_menu);
        } else { // Teacher or other roles
            fab.setVisibility(View.GONE);
            bottomNav.inflateMenu(R.menu.teacher_bottom_nav_menu);
        }

        bottomNav.setSelectedItemId(R.id.nav_schedule); // Set Schedule as selected

        fab.setOnClickListener(v -> {
            startActivity(new Intent(this, AddClassActivity.class));
        });

        // Add a listener for the bottom navigation
        bottomNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_schedule) {
                // Already here
            } else {
                Toast.makeText(this, item.getTitle() + " Clicked", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        loadClasses();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Refresh the list when coming back from AddClassActivity
        loadClasses();
    }

    private void loadClasses() {
        List<ClassItem> classList;
        // ✨ UPDATED LOGIC TO FETCH CLASSES
        if ("admin".equals(userRole)) {
            classList = db.getAllClasses(); // Admin sees all classes
        } else {
            classList = db.getClassesForTeacher(username); // Teacher sees only their classes
        }
        adapter = new ClassAdapter(classList);
        recyclerView.setAdapter(adapter);
    }

}