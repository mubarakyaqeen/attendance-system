package com.attendance.attendance_system.repository;
/*
 * Repository for attendance sessions
 */
import com.attendance.attendance_system.model.AttendanceSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

public interface AttendanceSessionRepository
        extends JpaRepository<AttendanceSession, Long> {

    Optional<AttendanceSession> findByCourseIdAndActiveTrue(Long courseId);

    List<AttendanceSession> findByActiveTrue();

    long countByLecturerIdAndActiveTrue(Long lecturerId);


    long countByActiveTrue();

    Optional<AttendanceSession> findFirstByLecturerIdAndActiveTrue(Long lecturerId);

    /*
     * Find active attendance session
     * for any of the courses the student is enrolled in
     */

    /*
     * Find the latest active attendance session
     * for any course the student is enrolled in
     */
    @Query("""
    SELECT s
    FROM AttendanceSession s
    WHERE s.course.id IN :courseIds
    AND s.active = true
    ORDER BY s.id DESC
""")
    List<AttendanceSession> findActiveSessionsForCourses(
            @Param("courseIds") List<Long> courseIds
    );

}