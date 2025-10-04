package com.example.myapplication;

public class User {
    private String name;
    private String employeeId;
    private String phone;
    private String dob;

    // ✨ New fields for student profile
    private String division;
    private String aadhaarNumber;
    private String parentsPhone;
    private String profileImagePath;

    public User(String name, String employeeId, String phone, String dob) {
        this.name = name;
        this.employeeId = employeeId;
        this.phone = phone;
        this.dob = dob;
    }

    // --- Getters ---
    public String getName() { return name; }
    public String getEmployeeId() { return employeeId; }
    public String getPhone() { return phone; }
    public String getDob() { return dob; }
    public String getDivision() { return division; }
    public String getAadhaarNumber() { return aadhaarNumber; }
    public String getParentsPhone() { return parentsPhone; }
    public String getProfileImagePath() { return profileImagePath; }

    // --- Setters ---
    public void setDivision(String division) { this.division = division; }
    public void setAadhaarNumber(String aadhaarNumber) { this.aadhaarNumber = aadhaarNumber; }
    public void setParentsPhone(String parentsPhone) { this.parentsPhone = parentsPhone; }
    public void setProfileImagePath(String profileImagePath) { this.profileImagePath = profileImagePath; }
}