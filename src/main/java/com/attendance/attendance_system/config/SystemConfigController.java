package com.attendance.attendance_system.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/system")
public class SystemConfigController {

    private final SystemConfigService configService;

    public SystemConfigController(SystemConfigService configService) {
        this.configService = configService;
    }

    @PutMapping("/toggle-registration")
    public String toggleRegistration() {

        configService.toggleRegistration();

        return "Registration toggled";
    }

    @GetMapping("/status")
    public boolean getStatus() {
        return configService.isRegistrationEnabled();
    }
}

