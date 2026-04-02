package com.attendance.attendance_system.dto;

/*
 * Student profile response
 */

public class StudentProfileResponse {

    private String fullName;
    private String matricNumber;
    private String email;
    private String department;
    private String level;
    private long present;
    private long late;
    private long absent;
    private String profileImageUrl;

    public StudentProfileResponse(String fullName, String matricNumber, String email,
                                  String department, String level,
                                  long present, long late,
                                  long absent, String profileImageUrl) {
        this.fullName = fullName;
        this.matricNumber = matricNumber;
        this.email = email;
        this.department = department;
        this.level = level;
        this.present = present;
        this.late = late;
        this.absent = absent;
        this.profileImageUrl = profileImageUrl;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public long getPresent() {
        return present;
    }

    public void setPresent(long present) {
        this.present = present;
    }

    public long getLate() {
        return late;
    }

    public void setLate(long late) {
        this.late = late;
    }

    public long getAbsent() {
        return absent;
    }

    public void setAbsent(long absent) {
        this.absent = absent;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}