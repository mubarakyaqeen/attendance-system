package com.attendance.attendance_system.dto;

/*
 * DTO used when lecturer starts an attendance session
 */
public class StartSessionRequest {

    /*
     * Course where attendance is being taken
     */
    private Long courseId;

    private int gracePeriodMinutes;

    /*
     * Lecturer starting the attendance
     */
    private Long lecturerId;

    /*
     * Duration of attendance session in minutes
     */
    private int durationMinutes;

    /*
     * Classroom latitude
     */
    private double latitude;

    /*
     * Classroom longitude
     */
    private double longitude;

    /*
     * Allowed attendance radius
     */
    private double radiusMeters;

    // getters and setters

    public Long getCourseId() { return courseId; }

    public void setCourseId(Long courseId) { this.courseId = courseId; }

    public Long getLecturerId() { return lecturerId; }

    public void setLecturerId(Long lecturerId) { this.lecturerId = lecturerId; }

    public int getDurationMinutes() { return durationMinutes; }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getRadiusMeters() { return radiusMeters; }

    public void setRadiusMeters(double radiusMeters) {
        this.radiusMeters = radiusMeters;
    }

    public int getGracePeriodMinutes() {
        return gracePeriodMinutes;
    }

    public void setGracePeriodMinutes(int gracePeriodMinutes) {
        this.gracePeriodMinutes = gracePeriodMinutes;
    }
}