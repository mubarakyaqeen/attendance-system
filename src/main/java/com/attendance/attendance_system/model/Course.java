package com.attendance.attendance_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
 * Course Entity
 *
 * Represents a course taught in the university.
 * Each course belongs to:
 *  - one department
 *  - one lecturer
 *
 * Example:
 * CSC301 - Computer Networks
 */

@Entity
@Table(name = "courses")

public class Course {

    /*
     * Primary key for course
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Course code
     * Example: CSC301
     */
    @Column(nullable = false, unique = true)
    private String code;

    /*
     * Course name
     * Example: Computer Networks
     */
    @Column(nullable = false)
    private String name;

    /*
     * Department offering this course
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    private String level;
    private String semester;

    public void setId(Long id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    /*
     * Lecturer teaching this course
     */
    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturer;

    /*
     * Timestamp for when course was created
     */
    private LocalDateTime createdAt = LocalDateTime.now();

    /*
     * Getters and Setters
     */

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}