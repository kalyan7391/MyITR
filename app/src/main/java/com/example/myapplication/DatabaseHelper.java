package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "school.db";
    // ✨ IMPORTANT: Incremented the database version
    private static final int DB_VERSION = 4;

    private static final String TABLE_TEACHERS = "teachers";
    private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_NOTIFICATIONS = "notifications";
    private static final String TABLE_ATTENDANCE = "attendance";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTeachers = "CREATE TABLE " + TABLE_TEACHERS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)";
        String createStudents = "CREATE TABLE " + TABLE_STUDENTS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)";
        String createNotifications = "CREATE TABLE " + TABLE_NOTIFICATIONS +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT, userType TEXT, subject TEXT)";

        // ✨ FIXED: Corrected attendance table schema
        String createAttendance = "CREATE TABLE " + TABLE_ATTENDANCE +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, student_username TEXT, teacher_username TEXT, subject TEXT, date TEXT, status TEXT)";

        db.execSQL(createTeachers);
        db.execSQL(createStudents);
        db.execSQL(createNotifications);
        db.execSQL(createAttendance);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ATTENDANCE);
        onCreate(db);
    }

    // Add teacher
    public boolean addTeacher(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long id = db.insert(TABLE_TEACHERS, null, cv);
        return id != -1;
    }

    // Add student
    public boolean addStudent(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long id = db.insert(TABLE_STUDENTS, null, cv);
        return id != -1;
    }

    // Check teacher credentials
    public boolean checkTeacherCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM " + TABLE_TEACHERS + " WHERE username=? AND password=?",
                new String[]{username, password});
        boolean ok = (c.getCount() > 0);
        c.close();
        return ok;
    }

    // Check student credentials
    public boolean checkStudentCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT id FROM " + TABLE_STUDENTS + " WHERE username=? AND password=?",
                new String[]{username, password});
        boolean ok = (c.getCount() > 0);
        c.close();
        return ok;
    }

    // Get all teacher usernames
    public List<String> getAllTeachers() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT username FROM " + TABLE_TEACHERS + " ORDER BY username", null);
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Get all students
    public List<String> getAllStudents() {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT username FROM " + TABLE_STUDENTS + " ORDER BY username", null);
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // Delete teacher by username
    public boolean deleteTeacher(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_TEACHERS, "username=?", new String[]{username});
        return rows > 0;
    }

    // Delete student by username
    public boolean deleteStudent(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete(TABLE_STUDENTS, "username=?", new String[]{username});
        return rows > 0;
    }

    public boolean addNotification(String message, String userType, String subject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("message", message);
        cv.put("userType", userType);
        cv.put("subject", subject);
        long id = db.insert(TABLE_NOTIFICATIONS, null, cv);
        return id != -1;
    }

    public List<String> getNotifications(String userType) {
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT subject, message FROM " + TABLE_NOTIFICATIONS + " WHERE userType=? ORDER BY id DESC", new String[]{userType});
        if (c.moveToFirst()) {
            do {
                list.add(c.getString(0) + ": " + c.getString(1));
            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    // ✨ FIXED: Corrected the addAttendance method signature and implementation
    public boolean addAttendance(String studentUsername,String teacherUsername, String subject, String date, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("student_username", studentUsername);
        cv.put("teacher_username", teacherUsername);
        cv.put("subject", subject);
        cv.put("date", date);
        cv.put("status", status);
        long id = db.insert(TABLE_ATTENDANCE, null, cv);
        return id != -1;
    }

    // ✨ FIXED: Updated getAttendance to retrieve and format the new data
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