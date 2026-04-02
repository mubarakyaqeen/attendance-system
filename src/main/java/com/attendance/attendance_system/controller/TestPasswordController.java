package com.attendance.attendance_system.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/*
 * Temporary controller to generate encrypted passwords.
 * This helps us insert users into the database.
 */

@RestController
@RequiredArgsConstructor
public class TestPasswordController {

    private final PasswordEncoder passwordEncoder;

    @GetMapping("/generate-password")
    public String generatePassword(@RequestParam String rawPassword) {

        // Encrypt the password using BCrypt
        return passwordEncoder.encode(rawPassword);
    }
}