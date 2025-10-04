package com.example.myapplication;

public class ClassItem {
    private String name;
    private String description;

    public ClassItem(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    // ✨ FIX: Added the missing getDescription() method
    public String getDescription() {
        return description;
    }
}