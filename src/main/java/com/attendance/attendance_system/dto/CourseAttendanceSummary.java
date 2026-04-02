package com.attendance.attendance_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CourseAttendanceSummary {

    private Long courseId;
    private String courseCode;
    private String courseName;

    private long totalSessions;
    private double averageAttendance;
}