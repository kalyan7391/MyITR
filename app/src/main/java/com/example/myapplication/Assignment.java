package com.example.myapplication;

public class Assignment {
    private String subject;
    private String title;
    private String dueDate;
    private String status;

    public Assignment(String subject, String title, String dueDate, String status) {
        this.subject = subject;
        this.title = title;
        this.dueDate = dueDate;
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }
}