package com.attendance.attendance_system.controller.student;

import com.attendance.attendance_system.dto.AttendanceHistoryResponse;
import com.attendance.attendance_system.dto.StudentDashboardResponse;
import com.attendance.attendance_system.dto.StudentProfileResponse;
import com.attendance.attendance_system.model.AttendanceRecord;
import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.service.StudentService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/*
 * Student Dashboard API
 *
 * This endpoint returns all data needed
 * for the Student mobile dashboard.
 */

@RestController
@RequestMapping("/student")

public class StudentDashboardController {

    private final StudentService studentService;

    public StudentDashboardController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
     * GET Student Dashboard
     */

    @GetMapping("/dashboard/{userId}")
    public StudentDashboardResponse getDashboard(
            @PathVariable Long userId
    ) {

        return studentService.getStudentDashboard(userId);

    }

    /*
     * Enroll student in course
     */
    @PostMapping("/enroll")
    public String enrollStudent(
            @RequestParam Long userId,
            @RequestParam Long courseId
    ) {

        studentService.enrollStudent(userId, courseId);

        return "Enrollment successful";
    }

    /*
     * Get filtered courses for enrollment
     */
    @GetMapping("/courses/{userId}")
    public List<Course> getCourses(@PathVariable Long userId) {
        return studentService.getAvailableCourses(userId);
    }


    /*
     * Get student attendance history
     */
    @GetMapping("/attendance/{userId}")
    public List<AttendanceHistoryResponse> getAttendanceHistory(
            @PathVariable Long userId
    ) {
        return studentService.getAttendanceHistory(userId);
    }


    @GetMapping("/profile/{userId}")
    public StudentProfileResponse getProfile(@PathVariable Long userId) {
        return studentService.getStudentProfile(userId);
    }

    @PutMapping("/profile/image")
    public String updateProfileImage(
            @RequestParam Long userId,
            @RequestParam String imageUrl
    ) {
        return studentService.saveProfileImage(userId, imageUrl);
    }


    @PostMapping("/profile/upload")
    public String uploadProfileImage(
            @RequestParam Long userId,
            @RequestParam MultipartFile file
    ) throws Exception {

        return studentService.uploadProfileImage(userId, file);
    }

    @GetMapping("/attendance/latest/{userId}")
    public AttendanceRecord getLatestAttendance(@PathVariable Long userId) {
        return studentService.getLatestAttendance(userId);
    }
}