package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/*
 * Repository for attendance records
 */

public interface AttendanceRecordRepository
        extends JpaRepository<AttendanceRecord, Long> {

    boolean existsBySessionIdAndStudentId(Long sessionId, Long studentId);

    long countByStatus(String present);

    long countBySessionId(Long sessionId);

    List<AttendanceRecord> findBySessionId(Long sessionId);

    @Query("""
    SELECT 
        r.session.course.id,
        r.session.course.code,
        r.session.course.name,
        COUNT(DISTINCT r.session.id),
        ROUND(
            (SUM(CASE WHEN r.status = 'PRESENT' THEN 1 ELSE 0 END) * 100.0) /
            COUNT(r.id), 2
        )
    FROM AttendanceRecord r
    WHERE r.session.course.lecturer.id = :lecturerId
    GROUP BY r.session.course.id, r.session.course.code, r.session.course.name
    """)
    List<Object[]> getCourseAttendanceSummary(Long lecturerId);


    /*
     * Get attendance history for a student
     */
    List<AttendanceRecord> findByStudentIdOrderByMarkedAtDesc(Long studentId);

    long countByStudentIdAndStatus(Long studentId, String present);

    Optional<AttendanceRecord> findTopByStudentIdOrderByMarkedAtDesc(Long studentId);
}