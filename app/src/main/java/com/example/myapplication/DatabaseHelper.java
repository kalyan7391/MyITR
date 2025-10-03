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
    private static final int DB_VERSION = 1;

    private static final String TABLE_TEACHERS = "teachers";
    private static final String TABLE_STUDENTS = "students";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTeachers = "CREATE TABLE " + TABLE_TEACHERS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)";
        String createStudents = "CREATE TABLE " + TABLE_STUDENTS +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)";
        db.execSQL(createTeachers);
        db.execSQL(createStudents);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEACHERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
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
}
