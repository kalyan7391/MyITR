package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import java.util.List;

public class SyllabusActivity extends AppCompatActivity {

    private ChipGroup chipGroup;
    private TextView tvSyllabusTitle;
    private LinearLayout syllabusContentLayout;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);

        db = new DatabaseHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        chipGroup = findViewById(R.id.chip_group_subjects);
        tvSyllabusTitle = findViewById(R.id.tv_syllabus_title);
        syllabusContentLayout = findViewById(R.id.layout_syllabus_content);

        populateSubjectChips();

        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            Chip selectedChip = findViewById(checkedId);
            if (selectedChip != null) {
                updateSyllabus(selectedChip.getText().toString());
            }
        });
    }

    private void populateSubjectChips() {
        List<String> subjects = db.getDistinctSubjects();
        chipGroup.removeAllViews();

        if (subjects.isEmpty()) {
            tvSyllabusTitle.setText("No subjects found");
            updateSyllabus("");
            return;
        }

        for (int i = 0; i < subjects.size(); i++) {
            String subject = subjects.get(i);
            Chip chip = new Chip(this);
            chip.setText(subject);
            chip.setCheckable(true);
            chip.setClickable(true);

            // ✨ FIX: This problematic line has been removed.
            // chip.setStyle(R.style.Widget_MaterialComponents_Chip_Choice);

            chipGroup.addView(chip);

            if (i == 0) {
                chip.setChecked(true);
            }
        }
    }

    private void updateSyllabus(String subject) {
        tvSyllabusTitle.setText(subject.isEmpty() ? "" : subject + " Syllabus");
        syllabusContentLayout.removeAllViews();

        if (subject.isEmpty()) {
            return;
        }

        List<String[]> syllabusData = db.getSyllabus(subject);
        LayoutInflater inflater = LayoutInflater.from(this);

        if (syllabusData.isEmpty()) {
            TextView noDataView = new TextView(this);
            noDataView.setText("No syllabus available for " + subject);
            syllabusContentLayout.addView(noDataView);
        } else {
            for (String[] unit : syllabusData) {
                View unitView = inflater.inflate(R.layout.list_item_syllabus, syllabusContentLayout, false);
                TextView tvUnitTitle = unitView.findViewById(R.id.tv_unit_title);
                TextView tvUnitDescription = unitView.findViewById(R.id.tv_unit_description);
                tvUnitTitle.setText(unit[0]);
                tvUnitDescription.setText(unit[1]);
                syllabusContentLayout.addView(unitView);
            }
        }
    }
}