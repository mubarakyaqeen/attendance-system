package com.attendance.attendance_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class SystemSetting {

    @Id
    private Long id = 1L;

    private boolean registrationBlocked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isRegistrationBlocked() {
        return registrationBlocked;
    }

    public void setRegistrationBlocked(boolean registrationBlocked) {
        this.registrationBlocked = registrationBlocked;
    }
}