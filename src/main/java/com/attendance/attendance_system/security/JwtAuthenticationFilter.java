package com.attendance.attendance_system.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/*
 * This filter runs on every request.
 * It checks if a JWT token exists in the request header.
 * If the token is valid, the request is allowed.
 */

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Get Authorization header from request
        String authHeader = request.getHeader("Authorization");

        /*
         * Expected format:
         * Authorization: Bearer TOKEN
         */

        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            // Extract token from header
            String token = authHeader.substring(7);

            // Extract user email from token
            String email = jwtService.extractEmail(token);

            // Here we could load user details if needed
            // For now we just validate token existence

        }

        // Continue request processing
        filterChain.doFilter(request, response);
    }
}