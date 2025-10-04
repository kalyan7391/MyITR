package com.example.myapplication;

public class User {
    private String name;
    private String employeeId;
    private String phone;
    private String dob;

    public User(String name, String employeeId, String phone, String dob) {
        this.name = name;
        this.employeeId = employeeId;
        this.phone = phone;
        this.dob = dob;
    }

    // --- Getter methods ---
    public String getName() {
        return name;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getPhone() {
        return phone;
    }

    public String getDob() {
        return dob;
    }
}