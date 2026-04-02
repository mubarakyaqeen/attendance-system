package com.attendance.attendance_system.dto;

import com.attendance.attendance_system.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

/*
 * Response returned after successful login
 */

@Data
@AllArgsConstructor
public class LoginResponse {

    private Long userId;
    private Long lecturerId;
    private String name;
    private String role;
    private String token;

}