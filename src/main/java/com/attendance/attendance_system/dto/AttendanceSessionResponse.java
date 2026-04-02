package com.attendance.attendance_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceSessionResponse {

    private Long id;

    private String courseCode;

    private String courseName;

    private String lecturerName;

    private String date;

    private long present;

    private long late ;

    private long absent;

    private boolean active;
}