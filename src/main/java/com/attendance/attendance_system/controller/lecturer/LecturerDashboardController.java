package com.attendance.attendance_system.controller.lecturer;

import com.attendance.attendance_system.dto.DashboardResponse;
import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.repository.CourseRepository;
import com.attendance.attendance_system.service.AttendanceService;
import com.attendance.attendance_system.service.CourseService;
import com.attendance.attendance_system.service.LecturerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lecturer")

public class LecturerDashboardController {

    private final LecturerService lecturerService;
    private final CourseRepository courseRepository ;


    public LecturerDashboardController(
            LecturerService lecturerService, AttendanceService attendanceService, CourseService courseService, CourseRepository courseRepository
    ) {
        this.lecturerService = lecturerService;
        this.courseRepository = courseRepository;
    }

    /*
     * Get lecturer dashboard statistics
     */
    @GetMapping("/dashboard/{lecturerId}")
    public DashboardResponse getDashboardStats(
            @PathVariable Long lecturerId
    ) {
        return lecturerService.getLecturerDashboardStats(lecturerId);
    }

    /*
     * Get available courses for student
     */
    @GetMapping("/courses")
    public List<Course> getCourses() {
        return courseRepository.findAll();
    }
}