package com.attendance.attendance_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttendanceTrendResponse {

    private String date;
    private long present;
}