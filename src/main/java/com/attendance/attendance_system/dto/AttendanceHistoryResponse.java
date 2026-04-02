package com.attendance.attendance_system.dto;

import java.time.LocalDateTime;

/*
 * DTO for student attendance history
 */
public class AttendanceHistoryResponse {

    private String courseCode;
    private String courseTitle;
    private String status;
    private LocalDateTime markedAt;

    public AttendanceHistoryResponse(
            String courseCode,
            String courseTitle,
            String status,
            LocalDateTime markedAt
    ) {
        this.courseCode = courseCode;
        this.courseTitle = courseTitle;
        this.status = status;
        this.markedAt = markedAt;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getMarkedAt() {
        return markedAt;
    }
}
