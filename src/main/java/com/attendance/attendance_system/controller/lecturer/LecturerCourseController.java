package com.attendance.attendance_system.controller.lecturer;

import com.attendance.attendance_system.dto.CreateCourseRequest;
import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.service.CourseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * APIs used by Lecturer to manage their own courses
 */

@RestController
@RequestMapping("/lecturer/courses")

public class LecturerCourseController {

    private final CourseService courseService;

    public LecturerCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    /*
     * Lecturer creates course
     */
    @PostMapping
    public Course createCourse(@RequestBody CreateCourseRequest request){

        return courseService.createCourse(request);
    }

    /*
     * Get courses created by lecturer
     */
    @GetMapping("/lecturer/{lecturerId}")
    public List<Course> getCoursesByLecturer(@PathVariable Long lecturerId){

        return courseService.getCoursesByLecturer(lecturerId);
    }

}