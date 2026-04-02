package com.attendance.attendance_system.service;

import com.attendance.attendance_system.dto.LoginRequest;
import com.attendance.attendance_system.dto.LoginResponse;
import com.attendance.attendance_system.model.Lecturer;
import com.attendance.attendance_system.model.User;
import com.attendance.attendance_system.repository.UserRepository;
import com.attendance.attendance_system.repository.LecturerRepository;
import com.attendance.attendance_system.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final LecturerRepository lecturerRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {

        // Find user
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid password");
        }

        // Generate token
        String token = jwtService.generateToken(user.getEmail());

        // Get lecturer profile if exists
        Lecturer lecturer = lecturerRepository.findByUserId(user.getId()).orElse(null);

        Long lecturerId = null;

        if (lecturer != null) {
            lecturerId = lecturer.getId();
        }

        return new LoginResponse(
                user.getId(),
                lecturerId,
                user.getFullName(),
                user.getRole().name(),
                token
        );
    }
}