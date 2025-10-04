package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "school.db";
    private static final int DB_VERSION = 8;

    // Table Names
    private static final String TABLE_TEACHERS = "teachers";
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String TABLE_ATTENDANCE = "attendance";
    private static final String TABLE_SCHEDULE = "schedule";
    private static final String TABLE_ASSIGNMENTS = "assignments";
    private static final String TABLE_SYLLABUS = "syllabus";


    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTeachers = "CREATE TABLE " + TABLE_TEACHERS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, employee_id TEXT, phone TEXT, dob TEXT)";
        String createStudents = "CREATE TABLE " + TABLE_STUDENTS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT, employee_id TEXT, phone TEXT, dob TEXT)";
        String createNotifications = "CREATE TABLE " + TABLE_NOTIFICATIONS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, userType TEXT, subject TEXT)";
        String createAttendance = "CREATE TABLE " + TABLE_ATTENDANCE +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, student_username TEXT, teacher_username TEXT, subject TEXT, date TEXT, status TEXT)";
        String createSchedule = "CREATE TABLE " + TABLE_SCHEDULE +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, class_name TEXT, class_time TEXT, teacher_name TEXT)";

        String createAssignments = "CREATE TABLE " + TABLE_ASSIGNMENTS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT, title TEXT, due_date TEXT, status TEXT)";
        String createSyllabus = "CREATE TABLE " + TABLE_SYLLABUS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, subject TEXT, unit_title TEXT, unit_description TEXT)";

        db.execSQL(createTeachers);
        db.execSQL(createStudents);
        db.execSQL(createNotifications);
        db.execSQL(createAttendance);
        db.execSQL(createSchedule);
        db.execSQL(createAssignments);
        db.execSQL(createSyllabus);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHEDULE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SYLLABUS);
        onCreate(db);
    }

    // --- Assignments ---
    public boolean addAssignment(String subject, String title, String dueDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subject", subject);
        cv.put("title", title);
        cv.put("due_date", dueDate);
        cv.put("status", status);
        return db.insert(TABLE_ASSIGNMENTS, null, cv) != -1;
    }

    public List<Assignment> getAssignments() {
        List<Assignment> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT subject, title, due_date, status FROM " + TABLE_ASSIGNMENTS, null);
        if (c.moveToFirst()) {
            do {
                list.add(new Assignment(c.getString(0), c.getString(1), c.getString(2), c.getString(3)));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // --- Syllabus ---
    public boolean addSyllabus(String subject, String title, String description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("subject", subject);
        cv.put("unit_title", title);
        cv.put("unit_description", description);
        return db.insert(TABLE_SYLLABUS, null, cv) != -1;
    }

    public List<String[]> getSyllabus(String subject) {
        List<String[]> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT unit_title, unit_description FROM " + TABLE_SYLLABUS + " WHERE subject=?", new String[]{subject});
        if (c.moveToFirst()) {
            do {
                list.add(new String[]{c.getString(0), c.getString(1)});
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<String> getDistinctSubjects() {
        List<String> subjects = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT DISTINCT subject FROM " + TABLE_SYLLABUS + " ORDER BY subject", null);
        if (c.moveToFirst()) {
            do {
                subjects.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        return subjects;
    }

    // --- Attendance ---
    public Map<String, String> getAttendanceSummary(String studentUsername) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor totalCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ATTENDANCE + " WHERE student_username=?", new String[]{studentUsername});
        int total = 0;
        if (totalCursor.moveToFirst()) {
            total = totalCursor.getInt(0);
        }
        totalCursor.close();

        Cursor presentCursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_ATTENDANCE + " WHERE student_username=? AND status='Present'", new String[]{studentUsername});
        int present = 0;
        if (presentCursor.moveToFirst()) {
            present = presentCursor.getInt(0);
        }
        presentCursor.close();

        Map<String, String> summary = new HashMap<>();
        if (total > 0) {
            int percentage = (present * 100) / total;
            summary.put("percentage", percentage + "%");
            summary.put("status", percentage >= 75 ? "Good" : "Low");
        } else {
            summary.put("percentage", "N/A");
            summary.put("status", "N/A");
        }
        return summary;
    }

    // --- User Management ---
    public boolean addTeacher(String username, String password, String empId, String phone, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("employee_id", empId);
        cv.put("phone", phone);
        cv.put("dob", dob);
        return db.insert(TABLE_TEACHERS, null, cv) != -1;
    }

    public boolean addStudent(String username, String password, String studentId, String phone, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        cv.put("employee_id", studentId);
        cv.put("phone", phone);
        cv.put("dob", dob);
        return db.insert(TABLE_STUDENTS, null, cv) != -1;
    }

    public List<User> getAllTeachers() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT username, employee_id, phone, dob FROM " + TABLE_TEACHERS + " ORDER BY username", null);
        if (c.moveToFirst()) {
            do {
                String name = c.getString(0);
                String empId = c.getString(1);
                String phone = c.getString(2);
                String dob = c.getString(3);
                list.add(new User(name, empId, phone, dob));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public List<User> getAllStudents() {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT username, employee_id, phone, dob FROM " + TABLE_STUDENTS + " ORDER BY username", null);
        if (c.moveToFirst()) {
            do {
                String name = c.getString(0);
                String studentId = c.getString(1);
                String phone = c.getString(2);
                String dob = c.getString(3);
                list.add(new User(name, studentId, phone, dob));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public boolean deleteTeacher(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_TEACHERS, "username=?", new String[]{username}) > 0;
    }

    public boolean deleteStudent(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDENTS, "username=?", new String[]{username}) > 0;
    }

    // --- Schedule ---
    public boolean addClass(String className, String classTime, String teacherName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("class_name", className);
        cv.put("class_time", classTime);
        cv.put("teacher_name", teacherName);
        return db.insert(TABLE_SCHEDULE, null, cv) != -1;
    }

    public List<ClassItem> getAllClasses() {
        List<ClassItem> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT class_name, class_time, teacher_name FROM " + TABLE_SCHEDULE, null);
        if (c.moveToFirst()) {
            do {
                String name = c.getString(0);
                String time = c.getString(1);
                String teacher = c.getString(2);
                classList.add(new ClassItem(name, "Taught by: " + teacher + " | " + time));
            } while (c.moveToNext());
        }
        c.close();
        return classList;
    }

    public List<ClassItem> getClassesForTeacher(String teacherName) {
        List<ClassItem> classList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT class_name, class_time FROM " + TABLE_SCHEDULE + " WHERE teacher_name=?", new String[]{teacherName});
        if (c.moveToFirst()) {
            do {
                String name = c.getString(0);
                String time = c.getString(1);
                classList.add(new ClassItem(name, time));
            } while (c.moveToNext());
        }
        c.close();
        return classList;
    }

    // --- Credentials ---
    public boolean checkTeacherCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM " + TABLE_TEACHERS + " WHERE username=? AND password=?", new String[]{username, password});
        boolean ok = c.getCount() > 0;
        c.close();
        return ok;
    }

    public boolean checkStudentCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM " + TABLE_STUDENTS + " WHERE username=? AND password=?", new String[]{username, password});
        boolean ok = c.getCount() > 0;
        c.close();
        return ok;
    }

    // --- Notifications ---
    public boolean addNotification(String message, String userType, String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("message", message);
        cv.put("userType", userType);
        cv.put("subject", subject);
        return db.insert(TABLE_NOTIFICATIONS, null, cv) != -1;
    }

    public List<Notice> getNotifications(String userType) {
        List<Notice> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT subject, message FROM " + TABLE_NOTIFICATIONS + " WHERE userType=? ORDER BY id DESC", new String[]{userType});
        if (c.moveToFirst()) {
            do {
                String title = c.getString(0);
                String message = c.getString(1);
                list.add(new Notice(title, "2024-10-26", message));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public boolean addAttendance(String studentUsername, String teacherUsername, String subject, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("student_username", studentUsername);
        cv.put("teacher_username", teacherUsername);
        cv.put("subject", subject);
        cv.put("date", date);
        cv.put("status", status);
        return db.insert(TABLE_ATTENDANCE, null, cv) != -1;
    }

    public List<String> getAttendance(String studentUsername) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT teacher_username, subject, date, status FROM " + TABLE_ATTENDANCE + " WHERE student_username=? ORDER BY date DESC", new String[]{studentUsername});
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0) + " (" + c.getString(1) + ") - " + c.getString(2) + ": " + c.getString(3));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }
}