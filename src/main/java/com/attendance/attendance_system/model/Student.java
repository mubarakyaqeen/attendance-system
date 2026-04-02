package com.attendance.attendance_system.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
 * Student Entity
 *
 * Represents a student in the university.
 * Each student:
 * - belongs to a department
 * - has a login account (User)
 */

@Entity
@Table(name = "students")

public class Student {

    /*
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Matric number
     * Example: CSC/2024/001
     */
    @Column(nullable = false, unique = true)
    private String matricNumber;

    /*
     * Student full name
     */
    @Column(nullable = false)
    private String fullName;

    /*
     * Student email
     */
    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = true)
    private String profileImageUrl;

    /*
     * Department the student belongs to
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /*
     * Login account for the student
     */
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private boolean blocked = false;

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Column(nullable = true)
    private String level;

    /*
     * Timestamp when student was created
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /*
     * Getters and Setters
     */

    public Long getId() {
        return id;
    }

    public String getMatricNumber() {
        return matricNumber;
    }

    public void setMatricNumber(String matricNumber) {
        this.matricNumber = matricNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }
}