package com.attendance.attendance_system.security;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/*
 * JwtService handles:
 * - generating JWT tokens
 * - extracting user information from tokens
 */

@Service
public class JwtService {

    /*
     * HS256 requires a key >= 256 bits.
     * Keys.secretKeyFor() automatically generates a secure key.
     */
    private final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /*
     * Generate JWT token for authenticated user
     */
    public String generateToken(String email) {

        return Jwts.builder()
                .setSubject(email) // store user email in token
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 24 hours
                .signWith(SECRET_KEY) // sign token securely
                .compact();
    }

    /*
     * Extract email from JWT token
     */
    public String extractEmail(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}