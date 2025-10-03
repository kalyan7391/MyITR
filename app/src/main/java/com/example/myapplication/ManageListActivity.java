package com.example.myapplication;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ManageListActivity extends AppCompatActivity {
    ListView listView;
    TextView tvTitle;
    DatabaseHelper db;
    String mode;
    ArrayAdapter<String> adapter;
    List<String> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_list);
        db = new DatabaseHelper(this);
        listView = findViewById(R.id.listItems);
        tvTitle = findViewById(R.id.tvListTitle);
        mode = getIntent().getStringExtra("mode");
        if (mode == null) mode = "teacher";
        tvTitle.setText(mode.equals("teacher") ? "Teachers" : "Students");

        // Initialize the items list and adapter here
        items = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(adapter);

        loadList();

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            String username = items.get(position);
            new AlertDialog.Builder(ManageListActivity.this)
                    .setTitle("Delete")
                    .setMessage("Delete " + username + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        boolean ok;
                        if (mode.equals("teacher")) {
                            ok = db.deleteTeacher(username);
                        } else {
                            ok = db.deleteStudent(username);
                        }

                        if (ok) {
                            Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();
                            loadList(); // Reload the list
                        } else {
                            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
            return true;
        });
    }

    private void loadList() {
        // Get the new data from the database
        List<String> newItems;
        if (mode.equals("teacher")) {
            newItems = db.getAllTeachers();
        } else {
            newItems = db.getAllStudents();
        }

        // Clear the old data from the adapter
        items.clear();
        // Add the new data
        items.addAll(newItems);
        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }
}