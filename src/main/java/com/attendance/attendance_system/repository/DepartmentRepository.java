package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository handles database operations for Department
 * Spring Data JPA automatically generates the queries
 */

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}