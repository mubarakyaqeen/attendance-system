package com.attendance.attendance_system.dto;

/*
 * DTO used when creating a student
 */

public class CreateStudentRequest {

    private String matricNumber;

    private String fullName;

    private String email;

    private String password;

    private Long departmentId;

    private String level;

    public String getMatricNumber() {
        return matricNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
}
