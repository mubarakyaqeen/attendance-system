package com.attendance.attendance_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
 * Enrollment Entity
 *
 * Represents a student registered for a course.
 * This is the bridge between:
 *
 * Student ↔ Course
 */

@Entity
@Table(name = "enrollments")

public class Enrollment {

    /*
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Student enrolled in the course
     */
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /*
     * Course the student enrolled in
     */
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    /*
     * Date student enrolled
     */
    private LocalDateTime enrolledAt = LocalDateTime.now();

    /*
     * Getters
     */

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public LocalDateTime getEnrolledAt() {
        return enrolledAt;
    }

    /*
     * Setters
     */

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}