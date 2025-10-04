package com.example.myapplication;

public class Notice {
    private String title;
    private String date;
    // ✨ FIX: Added a field for the full message
    private String message;

    public Notice(String title, String date, String message) {
        this.title = title;
        this.date = date;
        this.message = message;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    // ✨ FIX: Added a getter for the message
    public String getMessage() {
        return message;
    }
}