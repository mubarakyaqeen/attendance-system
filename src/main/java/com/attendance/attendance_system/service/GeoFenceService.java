package com.attendance.attendance_system.service;

/*
 * Service to check if a student is within classroom range
 */

public class GeoFenceService {

    private static final double EARTH_RADIUS = 6371000; // meters

    /*
     * Calculate distance between two coordinates
     */
    public double calculateDistance(
            double lat1, double lon1,
            double lat2, double lon2
    ) {

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2)
                * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    /*
     * Check if student is inside classroom radius
     */
    public boolean isInsideClassroom(
            double studentLat,
            double studentLon,
            double classLat,
            double classLon,
            double radiusMeters
    ) {

        double distance = calculateDistance(
                studentLat,
                studentLon,
                classLat,
                classLon
        );

        return distance <= radiusMeters;
    }
}