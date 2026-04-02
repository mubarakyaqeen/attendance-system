package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/*
 * Repository for Student entity
 */

public interface StudentRepository extends JpaRepository<Student, Long> {

    boolean existsByEmail(String email);

    boolean existsByMatricNumber(String matricNumber);

    /*
     * Find student using the login account
     *
     * This allows us to get the student profile
     * from the authenticated user.
     */

    Optional<Student> findByUserId(Long userId);



}