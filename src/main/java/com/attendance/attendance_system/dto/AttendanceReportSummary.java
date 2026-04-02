package com.attendance.attendance_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceReportSummary {

    private double overallAttendance;
    private long coursesMonitored;
    private long totalStudents;
    private long attendanceSessions;
}

