package com.attendance.attendance_system.controller.admin;

import com.attendance.attendance_system.config.SystemConfigService;
import com.attendance.attendance_system.dto.CreateStudentRequest;
import com.attendance.attendance_system.dto.StudentResponse;
import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.model.Student;
import com.attendance.attendance_system.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * REST API for student management
 */

@RestController
@RequestMapping("/admin/students")
public class StudentController {

    private final StudentService studentService;
    private final SystemConfigService configService ;

    public StudentController(StudentService studentService, SystemConfigService configService) {
        this.studentService = studentService;
        this.configService = configService;
    }


    /*
     * Create student
     */
    @PostMapping
    public StudentResponse createStudent(@RequestBody CreateStudentRequest request) {

        if (!configService.isRegistrationEnabled()) {
            throw new RuntimeException("REGISTRATION_BLOCKED");
        }

        Student student = studentService.createStudent(request);

        return new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getMatricNumber(),
                student.getEmail(),
                student.getDepartment().getName(),
                student.isBlocked()
        );
    }

    /*
     * Get all students
     */
    @GetMapping
    public List<StudentResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    /*
     * Get student by ID
     */
    @GetMapping("/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {

        Student student = studentService.getStudentById(id);

        return new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getMatricNumber(),
                student.getEmail(),
                student.getDepartment().getName(),
                student.isBlocked()
        );
    }

    @GetMapping("/courses/{userId}")
    public List<Course> getCourses(@PathVariable Long userId) {
        return studentService.getAvailableCourses(userId);
    }

    @PutMapping("/{id}")
    public Student updateStudent(
            @PathVariable Long id,
            @RequestBody CreateStudentRequest request
    ) {
        return studentService.updateStudent(id, request);
    }

    /*
     * Delete student
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {

        studentService.deleteStudent(id);

        return ResponseEntity.ok().build();
    }

    /*
     * Block / Unblock student
     */
    @PutMapping("/{id}/toggle-block")
    public ResponseEntity<StudentResponse> toggleBlockStudent(@PathVariable Long id) {

        Student student = studentService.toggleBlockStudent(id);

        StudentResponse response = new StudentResponse(
                student.getId(),
                student.getFullName(),
                student.getMatricNumber(),
                student.getEmail(),
                student.getDepartment().getName(),
                student.isBlocked()
        );

        return ResponseEntity.ok(response);
    }
}