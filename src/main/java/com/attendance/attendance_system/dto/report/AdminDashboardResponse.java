package com.attendance.attendance_system.dto.report;

/*
 * Response returned for admin dashboard statistics
 */

public class AdminDashboardResponse {

    private long totalStudents;
    private long totalLecturers;
    private long totalCourses;
    private long totalDepartments;
    private double attendanceRate;

    public AdminDashboardResponse(
            long totalStudents,
            long totalLecturers,
            long totalCourses,
            long totalDepartments,
            double attendanceRate
    ) {
        this.totalStudents = totalStudents;
        this.totalLecturers = totalLecturers;
        this.totalCourses = totalCourses;
        this.totalDepartments = totalDepartments;
        this.attendanceRate = attendanceRate;
    }

    public long getTotalStudents() { return totalStudents; }
    public long getTotalLecturers() { return totalLecturers; }
    public long getTotalCourses() { return totalCourses; }
    public long getTotalDepartments() { return totalDepartments; }
    public double getAttendanceRate() { return attendanceRate; }
}