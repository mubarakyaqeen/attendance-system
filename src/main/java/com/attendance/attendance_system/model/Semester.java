package com.attendance.attendance_system.model;


import jakarta.persistence.*;
import lombok.*;

/*
 * Semester entity represents an academic period.
 * Example:
 * 2025/2026 - First Semester
 */

@Entity
@Table(name = "semesters")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Semester {

    /*
     * Primary key for the semester
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Academic year
     * Example: 2025/2026
     */
    @Column(nullable = false)
    private String academicYear;

    /*
     * Semester name
     * Example: First Semester
     */
    @Column(nullable = false)
    private String semesterName;

}