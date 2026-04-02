package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.Semester;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository layer for Semester database operations
 */

public interface SemesterRepository extends JpaRepository<Semester, Long> {
}