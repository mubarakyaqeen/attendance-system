package com.attendance.attendance_system.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/*
 * AttendanceRecord
 *
 * Stores the attendance of each student
 * for a specific session
 */

@Entity
@Table(name = "attendance_records")

public class AttendanceRecord {

    /*
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Session the record belongs to
     */
    @ManyToOne
    @JoinColumn(name = "session_id", nullable = false)
    private AttendanceSession session;

    /*
     * Student marking attendance
     */
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    /*
     * Attendance status
     */
    private String status;

    /*
     * Time student marked attendance
     */
    private LocalDateTime markedAt = LocalDateTime.now();

    /*
     * Getters and setters
     */

    public Long getId() {
        return id;
    }

    public AttendanceSession getSession() {
        return session;
    }

    public Student getStudent() {
        return student;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getMarkedAt() {
        return markedAt;
    }

    public void setSession(AttendanceSession session) {
        this.session = session;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}