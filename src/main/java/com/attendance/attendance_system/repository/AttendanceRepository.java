package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.AttendanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository
        extends JpaRepository<AttendanceRecord, Long> {

    boolean existsByStudentIdAndSessionId(Long studentId, Long sessionId);
}