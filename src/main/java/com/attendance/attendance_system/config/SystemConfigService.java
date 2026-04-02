package com.attendance.attendance_system.config;

import org.springframework.stereotype.Service;

@Service
public class SystemConfigService {

    private boolean registrationEnabled = true;

    public boolean isRegistrationEnabled() {
        return registrationEnabled;
    }

    public void toggleRegistration() {
        registrationEnabled = !registrationEnabled;
    }

}