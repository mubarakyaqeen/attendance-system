package com.attendance.attendance_system.dto;

/*
 * DTO used to enroll a student into a course
 */

public class EnrollmentRequest {

    private Long studentId;

    private Long courseId;

    public Long getStudentId() {
        return studentId;
    }

    public Long getCourseId() {
        return courseId;
    }
}