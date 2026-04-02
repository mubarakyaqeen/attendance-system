package com.attendance.attendance_system.dto;

public class LecturerResponse {

    private Long id;
    private String fullName;
    private String email;
    private String department;

    public LecturerResponse(Long id, String fullName, String email, String department) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.department = department;
    }

    public Long getId() { return id; }

    public String getFullName() { return fullName; }

    public String getEmail() { return email; }

    public String getDepartment() { return department; }
}