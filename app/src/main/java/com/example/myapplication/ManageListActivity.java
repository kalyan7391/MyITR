package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ManageListActivity extends AppCompatActivity implements UserAdapter.OnUserActionsListener {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private DatabaseHelper db;
    private String mode; // "teacher" or "student"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        db = new DatabaseHelper(this);
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "teacher";

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(mode.equals("teacher") ? "Manage Teachers" : "Manage Students");
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView = findViewById(R.id.recycler_view_users);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EditText etSearch = findViewById(R.id.et_search);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (adapter != null) {
                    adapter.filter(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        loadList();
    }

    private void loadList() {
        List<String> userList;
        if (mode.equals("teacher")) {
            userList = db.getAllTeachers();
        } else {
            userList = db.getAllStudents();
        }
        // "this" refers to the activity, which implements the listener interface
        adapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditUser(String username) {
        // You can implement an edit screen here in the future
        Toast.makeText(this, "Edit " + username, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteUser(String username) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete " + username + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean isDeleted;
                    if (mode.equals("teacher")) {
                        isDeleted = db.deleteTeacher(username);
                    } else {
                        isDeleted = db.deleteStudent(username);
                    }

                    if (isDeleted) {
                        Toast.makeText(this, username + " deleted", Toast.LENGTH_SHORT).show();
                        loadList(); // Refresh the list
                    } else {
                        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
