package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/*
 * Repository for Course entity
 *
 * Provides CRUD operations automatically
 */

public interface CourseRepository extends JpaRepository<Course, Long> {
    long countByLecturerId(Long lecturerId);

    List<Course> findByLecturerId(Long lecturerId);

    /*
     * Find courses by department and level
     */
    List<Course> findByDepartmentIdAndLevel(Long departmentId, String level);

}