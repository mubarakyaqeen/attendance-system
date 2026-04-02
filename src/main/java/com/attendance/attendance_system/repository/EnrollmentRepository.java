package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Repository for enrollment table
 */

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    long countByCourseId(Long id);

    long countByCourseLecturerId(Long lecturerId);

    List<Enrollment> findByCourseId(Long courseId);

    /*
     * Get all courses a student is enrolled in
     */
    List<Enrollment> findByStudentId(Long studentId);

}