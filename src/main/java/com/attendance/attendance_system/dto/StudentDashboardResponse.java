package com.attendance.attendance_system.dto;

import java.util.List;

/*
 * Response returned for Student Dashboard
 * Everything here is fetched dynamically from the database
 */

public class StudentDashboardResponse {

    /*
     * Student basic information
     */
    private String fullName;
    private String matricNumber;

    /*
     * Active attendance session
     */
    private Long courseId;
    private Long sessionId;
    private String courseCode;
    private String courseTitle;
    private String lecturerName;
    private int radius;

    /*
     * Courses student is enrolled in
     */
    private List<String> courses;
    private boolean alreadyMarked;

    /*
     * For attendance count down
     */
    private String startedAt;
    private int durationMinutes;


    public StudentDashboardResponse(
            String fullName,
            String matricNumber,
            Long courseId,
            Long sessionId,
            String courseCode,
            String courseTitle,
            String lecturerName,
            int radius,
            List<String> courses,
            boolean alreadyMarked ,
            String startedAt ,
            int durationMinutes
    ) {
        this.fullName = fullName;
        this.matricNumber = matricNumber;
        this.courseId = courseId;
        this.sessionId = sessionId;
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.lecturerName = lecturerName;
        this.radius = radius;
        this.courses = courses;
        this.alreadyMarked = alreadyMarked;
        this.startedAt = startedAt;
        this.durationMinutes = durationMinutes;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public int getRadius() {
        return radius;
    }

    public List<String> getCourses() {
        return courses;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setCourses(List<String> courses) {
        this.courses = courses;
    }

    public boolean isAlreadyMarked() {
        return alreadyMarked;
    }

    public void setAlreadyMarked(boolean alreadyMarked) {
        this.alreadyMarked = alreadyMarked;
    }

    public String getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(String startedAt) {
        this.startedAt = startedAt;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
}