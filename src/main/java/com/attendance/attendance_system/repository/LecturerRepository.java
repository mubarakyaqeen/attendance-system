package com.attendance.attendance_system.repository;

import com.attendance.attendance_system.model.Lecturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/*
 * Repository for Lecturer database operations
 */

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    @Query("""
SELECT COUNT(c)
FROM Course c
WHERE c.lecturer.id = :lecturerId
""")
    long countCoursesByLecturer(Long lecturerId);


    Optional<Lecturer> findByUserId(Long userId);





    @Query("""
SELECT COUNT(e)
FROM Enrollment e
WHERE e.course.lecturer.id = :lecturerId
""")
    long countStudentsByLecturer(Long lecturerId);

}
