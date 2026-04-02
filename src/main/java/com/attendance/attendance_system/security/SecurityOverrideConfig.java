package com.attendance.attendance_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/*
 * This class overrides Spring Boot's default in-memory user configuration.
 * By providing our own UserDetailsService, Spring will stop generating
 * the default user and password.
 */

@Configuration
public class SecurityOverrideConfig {

    @Bean
    public UserDetailsService userDetailsService() {

        return username -> {
            throw new RuntimeException("UserDetailsService not implemented yet");
        };

    }

}