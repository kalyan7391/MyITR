package com.example.myapplication;

public class AttendanceRecord {
    private String studentName;
    private String date;
    private String status;

    public AttendanceRecord(String studentName, String date, String status) {
        this.studentName = studentName;
        this.date = date;
        this.status = status;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}