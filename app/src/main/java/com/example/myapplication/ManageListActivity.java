package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
// ✨ FIX: Import MaterialToolbar
import com.google.android.material.appbar.MaterialToolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ManageListActivity extends AppCompatActivity implements UserAdapter.OnUserActionsListener {

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private DatabaseHelper db;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);

        db = new DatabaseHelper(this);
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "teacher";

        // ✨ FIX: Use MaterialToolbar class
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
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
        List<User> userList;
        if (mode.equals("teacher")) {
            userList = db.getAllTeachers();
        } else {
            userList = db.getAllStudents();
        }
        adapter = new UserAdapter(userList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onEditUser(User user) {
        Toast.makeText(this, "Edit " + user.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete " + user.getName() + "?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    boolean isDeleted;
                    if (mode.equals("teacher")) {
                        isDeleted = db.deleteTeacher(user.getName());
                    } else {
                        isDeleted = db.deleteStudent(user.getName());
                    }

                    if (isDeleted) {
                        Toast.makeText(this, user.getName() + " deleted", Toast.LENGTH_SHORT).show();
                        loadList();
                    } else {
                        Toast.makeText(this, "Failed to delete", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}