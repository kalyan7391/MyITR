package com.example.myapplication;

public class Assignment {
    private String subject;
    private String title;
    private String dueDate;
    private String status;
    // ✨ New fields
    private String teacherName;
    private String filePath;

    // ✨ Updated constructor
    public Assignment(String subject, String title, String dueDate, String status, String teacherName, String filePath) {
        this.subject = subject;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
        this.teacherName = teacherName;
        this.filePath = filePath;
    }

    // --- Getters ---
    public String getSubject() { return subject; }
    public String getTitle() { return title; }
    public String getDueDate() { return dueDate; }
    public String getStatus() { return status; }
    public String getTeacherName() { return teacherName; }
    public String getFilePath() { return filePath; }
}