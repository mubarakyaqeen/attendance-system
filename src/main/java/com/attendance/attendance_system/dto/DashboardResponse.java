package com.attendance.attendance_system.dto;

/*
 * Response used for dashboard statistics
 */

public class DashboardResponse {

    private String name;
    private long students;
    private long lecturers;
    private long courses;
    private long sessions;

    public DashboardResponse(
            String name,
            long students,
            long lecturers,
            long courses,
            long sessions
    ) {
        this.name = name;
        this.students = students;
        this.lecturers = lecturers;
        this.courses = courses;
        this.sessions = sessions;
    }

    public String getName() {
        return name;
    }

    public long getStudents() {
        return students;
    }

    public long getLecturers() {
        return lecturers;
    }

    public long getCourses() {
        return courses;
    }

    public long getSessions() {
        return sessions;
    }
}