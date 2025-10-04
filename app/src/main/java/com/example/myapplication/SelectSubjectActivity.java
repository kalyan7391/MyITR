package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.stream.Collectors;

public class SelectSubjectActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private String teacherUsername, attendanceType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_subject);

        db = new DatabaseHelper(this);
        teacherUsername = getIntent().getStringExtra("username");
        attendanceType = getIntent().getStringExtra("type");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recycler_view_subjects);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get unique subject names for the teacher
        List<ClassItem> classes = db.getClassesForTeacher(teacherUsername);
        List<String> subjectNames = classes.stream()
                .map(ClassItem::getName)
                .distinct()
                .collect(Collectors.toList());

        // We can reuse the ClassAdapter for this simple list
        // The second parameter (description) will be ignored, but the click listener works on the name
        List<ClassItem> subjectItems = subjectNames.stream()
                .map(name -> new ClassItem(name, ""))
                .collect(Collectors.toList());

        ClassAdapter adapter = new ClassAdapter(subjectItems);
        recyclerView.setAdapter(adapter);

        // Handle click on a subject
        adapter.setOnItemClickListener(subjectItem -> {
            Intent i = new Intent(SelectSubjectActivity.this, GenerateCodeActivity.class);
            i.putExtra("teacher_username", teacherUsername);
            i.putExtra("subject", subjectItem.getName()); // Pass the selected subject
            i.putExtra("type", attendanceType);
            startActivity(i);
            finish(); // Close this screen
        });
    }
}