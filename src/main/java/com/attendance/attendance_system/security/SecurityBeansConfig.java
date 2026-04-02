package com.attendance.attendance_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
 * This configuration class provides security-related beans.
 * Here we create the PasswordEncoder bean used to hash passwords.
 */

@Configuration
public class SecurityBeansConfig {

    /*
     * BCryptPasswordEncoder is the recommended encoder
     * used by Spring Security for hashing passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}