package com.attendance.attendance_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LiveSessionResponse {

    private String course;
    private String lecturer;
    private long present;
    private long late;
    private long absent;

}