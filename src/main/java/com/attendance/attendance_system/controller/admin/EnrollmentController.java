package com.attendance.attendance_system.controller.admin;

import com.attendance.attendance_system.dto.EnrollmentRequest;
import com.attendance.attendance_system.model.Enrollment;
import com.attendance.attendance_system.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Enrollment API endpoints
 */

@RestController
@RequestMapping("/admin/enrollments")

public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    /*
     * Enroll student in course
     */
    @PostMapping
    public Enrollment enrollStudent(@RequestBody EnrollmentRequest request) {
        return enrollmentService.enrollStudent(request);
    }

    /*
     * Get all enrollments
     */
    @GetMapping
    public List<Enrollment> getEnrollments() {
        return enrollmentService.getAllEnrollments();
    }

    /*
     * Delete enrollment
     */
    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }
}