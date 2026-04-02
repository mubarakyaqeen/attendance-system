package com.attendance.attendance_system.dto;

import lombok.Data;

/*
 * Request object for creating lecturer
 */

@Data
public class CreateLecturerRequest {

    private String fullName;

    private String email;

    private String password;

    private Long departmentId;

}