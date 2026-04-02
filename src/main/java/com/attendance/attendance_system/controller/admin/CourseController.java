package com.attendance.attendance_system.controller.admin;

import com.attendance.attendance_system.dto.CreateCourseRequest;
import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * REST API endpoints for course management
 *
 * Only ADMIN can manage courses
 */

@RestController
@RequestMapping("/admin/courses")

public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /*
     * Create new course
     */
    @PostMapping
    public Course createCourse(@RequestBody CreateCourseRequest request) {
        return courseService.createCourse(request);
    }

    /*
     * Get all courses
     */
    @GetMapping
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    /*
     * Get course by ID
     */
    @GetMapping("/{id}")
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    /*
     * Update course
     */
    @PutMapping("/{id}")
    public Course updateCourse(
            @PathVariable Long id,
            @RequestBody CreateCourseRequest request
    ) {
        return courseService.updateCourse(id, request);
    }

    /*
     * Delete course
     */
    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @GetMapping("/lecturer/{lecturerId}")
    public List<Course> getCoursesByLecturer(@PathVariable Long lecturerId) {
        return courseService.getCoursesByLecturer(lecturerId);
    }
}