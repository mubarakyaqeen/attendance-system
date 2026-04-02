package com.attendance.attendance_system.service;

import com.attendance.attendance_system.dto.CreateLecturerRequest;
import com.attendance.attendance_system.dto.DashboardResponse;
import com.attendance.attendance_system.model.*;
import com.attendance.attendance_system.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Lecturer business logic
 */

@Service
public class LecturerService {

    private final LecturerRepository lecturerRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceSessionRepository sessionRepository;

    public LecturerService(
            LecturerRepository lecturerRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, AttendanceSessionRepository sessionRepository
    ) {
        this.lecturerRepository = lecturerRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.courseRepository = courseRepository;

        this.enrollmentRepository = enrollmentRepository;
        this.sessionRepository = sessionRepository;
    }

    /*
     * Create lecturer
     */
    public Lecturer createLecturer(CreateLecturerRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with this email already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRole(Role.LECTURER);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        Lecturer lecturer = new Lecturer();
        lecturer.setFullName(request.getFullName());
        lecturer.setEmail(request.getEmail());
        lecturer.setDepartment(department);
        lecturer.setUser(user);

        return lecturerRepository.save(lecturer);
    }

    /*
     * Get all lecturers
     */
    public List<Lecturer> getAllLecturers() {
        return lecturerRepository.findAll();
    }

    /*
     * Get lecturer by ID
     */
    public Lecturer getLecturerById(Long id) {
        return lecturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));
    }


    public Lecturer updateLecturer(Long id, CreateLecturerRequest request) {

        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        lecturer.setFullName(request.getFullName());
        lecturer.setEmail(request.getEmail());

        return lecturerRepository.save(lecturer);
    }


    /*
     * Delete lecturer
     */
    public void deleteLecturer(Long id) {

        Lecturer lecturer = lecturerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        lecturerRepository.delete(lecturer);
    }


    public DashboardResponse getLecturerDashboardStats(Long lecturerId) {

        Lecturer lecturer = lecturerRepository.findById(lecturerId)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        long courses = courseRepository.countByLecturerId(lecturerId);

        long students = enrollmentRepository.countByCourseLecturerId(lecturerId);

        long sessions = sessionRepository.countByLecturerIdAndActiveTrue(lecturerId);

        return new DashboardResponse(
                lecturer.getFullName(),
                students,
                1,
                courses,
                sessions
        );
    }

}

