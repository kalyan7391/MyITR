package com.example.myapplication;

public class Student {
    private String name;
    private String roll;

    public Student(String name, String roll) {
        this.name = name;
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public String getRoll() {
        return roll;
    }
}