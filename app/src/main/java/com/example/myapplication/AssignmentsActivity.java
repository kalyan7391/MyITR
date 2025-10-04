package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.tabs.TabLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AssignmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private DatabaseHelper db;
    private TabLayout tabLayout;
    private List<Assignment> assignmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignments);

        db = new DatabaseHelper(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view_assignments);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        tabLayout = findViewById(R.id.tab_layout_assignments);

        // Load all assignments from the database
        assignmentList = db.getAssignments();

        // Initially sort by subject
        sortAssignments("subject");

        adapter = new AssignmentAdapter(assignmentList);
        recyclerView.setAdapter(adapter);

        // Add tab selection listener to re-sort the list
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    sortAssignments("subject");
                } else {
                    sortAssignments("duedate");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    private void sortAssignments(String criteria) {
        if ("subject".equals(criteria)) {
            // Sort by Subject, then by Title
            Collections.sort(assignmentList, Comparator.comparing(Assignment::getSubject)
                    .thenComparing(Assignment::getTitle));
        } else { // "duedate"
            // Sort by Due Date
            // Note: This is a simple string sort. For real date sorting, dates should be stored in YYYY-MM-DD format.
            Collections.sort(assignmentList, Comparator.comparing(Assignment::getDueDate));
        }
    }
}