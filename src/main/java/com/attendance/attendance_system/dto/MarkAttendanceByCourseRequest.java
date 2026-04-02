package com.attendance.attendance_system.dto;

/*
 * Request used when student marks attendance
 */

public class MarkAttendanceByCourseRequest {

    private Long userId;
    private Long courseId;

    private double latitude;
    private double longitude;

    /*
     * Getters
     */

    public Long getUserId() {
        return userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /*
     * Setters
     */

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}