package com.attendance.attendance_system.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/*
 * Security configuration for development
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for REST APIs
                .csrf(csrf -> csrf.disable())

                // Disable login page
                .formLogin(form -> form.disable())

                // Disable HTTP basic authentication
                .httpBasic(basic -> basic.disable())

                .authorizeHttpRequests(auth -> auth

                        // Public endpoints
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/generate-password").permitAll()

                        // TEMP: allow admin APIs during development
                        .requestMatchers("/admin/**").permitAll()

                        // Allow other endpoints for now
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}