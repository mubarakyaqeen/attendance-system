package com.attendance.attendance_system.controller.lecturer;

import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.repository.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Lecturer Course Controller
 */

@RestController
@RequestMapping("/lecturer")
public class LecturerCourseControl {

    private final CourseRepository courseRepository;

    public LecturerCourseControl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    /*
     * Fetch all courses taught by a lecturer
     */
    @GetMapping("/{lecturerId}/courses")
    public List<Course> getLecturerCourses(
            @PathVariable Long lecturerId
    ) {
        return courseRepository.findByLecturerId(lecturerId);
    }
}