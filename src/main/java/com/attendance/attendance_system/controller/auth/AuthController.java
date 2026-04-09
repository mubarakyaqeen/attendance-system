package com.attendance.attendance_system.controller.auth;

import com.attendance.attendance_system.dto.LoginRequest;
import com.attendance.attendance_system.dto.LoginResponse;
import com.attendance.attendance_system.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request){

        return authService.login(request);

    }

}