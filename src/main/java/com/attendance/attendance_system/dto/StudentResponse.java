package com.attendance.attendance_system.dto;

public class StudentResponse {

    private Long id;
    private String fullName;
    private String matricNumber;
    private String email;
    private String department;
    private boolean blocked;

    public StudentResponse(Long id, String fullName, String matricNumber,
                           String email, String department, boolean blocked) {

        this.id = id;
        this.fullName = fullName;
        this.matricNumber = matricNumber;
        this.email = email;
        this.department = department;
        this.blocked = blocked;
    }

    public Long getId() { return id; }

    public String getFullName() { return fullName; }

    public String getMatricNumber() { return matricNumber; }

    public String getEmail() { return email; }

    public String getDepartment() { return department; }

    public boolean isBlocked() { return blocked; }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}