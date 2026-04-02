package com.attendance.attendance_system.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/*
 * Represents a single attendance session created by a lecturer
 */

@Entity
@Table(name = "attendance_sessions")

public class AttendanceSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Course associated with the attendance
     */
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    /*
     * Lecturer who started the session
     */
    @ManyToOne
    @JoinColumn(name = "lecturer_id")
    private Lecturer lecturer;

    @Column(nullable = false)
    private boolean active;

    /*
     * Classroom latitude
     */
    private double latitude;

    /*
     * Classroom longitude
     */
    private double longitude;

    /*
     * GeoFence radius
     */
    @Column(nullable = false)
    private double radiusMeters = 50;

    /*
     * Date attendance is taken
     */
    private LocalDate sessionDate;

    /*
     * Time when session started
     */
    private LocalDateTime startedAt;

    /*
     * Duration of attendance window in minutes
     */
    private int durationMinutes;


    /*
     * Time when attendance session ended
     */
    private LocalDateTime endedAt;

    private int gracePeriodMinutes = 10;


    // =========================
    // Getters and Setters
    // =========================

    public Long getId() {
        return id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getRadiusMeters() {
        return radiusMeters;
    }

    public void setRadiusMeters(double radiusMeters) {
        this.radiusMeters = radiusMeters;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getEndedAt() {
        return endedAt;
    }

    public void setEndedAt(LocalDateTime endedAt) {
        this.endedAt = endedAt;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public LocalDate getSessionDate() {
        return sessionDate;
    }

    public void setSessionDate(LocalDate sessionDate) {
        this.sessionDate = sessionDate;
    }

    public LocalDateTime getStartedAt() {
        return startedAt;
    }

    public void setStartedAt(LocalDateTime startedAt) {
        this.startedAt = startedAt;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getGracePeriodMinutes() {
        return gracePeriodMinutes;
    }

    public void setGracePeriodMinutes(int gracePeriodMinutes) {
        this.gracePeriodMinutes = gracePeriodMinutes;
    }
}