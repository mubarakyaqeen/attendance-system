package com.attendance.attendance_system.service;

import com.attendance.attendance_system.dto.*;
import com.attendance.attendance_system.model.*;
import com.attendance.attendance_system.repository.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final DepartmentRepository departmentRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EnrollmentRepository enrollmentRepository;
    private final AttendanceSessionRepository attendanceSessionRepository;
    private final CourseRepository courseRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final AttendanceService attendanceService ;

    public StudentService(
            StudentRepository studentRepository,
            DepartmentRepository departmentRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            EnrollmentRepository enrollmentRepository,
            AttendanceSessionRepository attendanceSessionRepository,
            CourseRepository courseRepository,
            AttendanceRecordRepository attendanceRecordRepository, AttendanceService attendanceService
    ) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.enrollmentRepository = enrollmentRepository;
        this.attendanceSessionRepository = attendanceSessionRepository;
        this.courseRepository = courseRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.attendanceService = attendanceService;
    }

    /*
     * CREATE STUDENT
     */
    public Student createStudent(CreateStudentRequest request) {

        if (studentRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        if (studentRepository.existsByMatricNumber(request.getMatricNumber())) {
            throw new RuntimeException("Matric number already exists");
        }

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        User user = new User();
        user.setEmail(request.getEmail());
        user.setFullName(request.getFullName());
        user.setRole(Role.STUDENT);
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);

        Student student = new Student();
        student.setMatricNumber(request.getMatricNumber());
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setDepartment(department);
        student.setUser(user);
        student.setBlocked(false);

        /*
         * Save level
         */
        student.setLevel(request.getLevel());

        return studentRepository.save(student);
    }

    /*
     * GET ALL STUDENTS
     */
    public List<StudentResponse> getAllStudents() {

        return studentRepository.findAll()
                .stream()
                .map(student -> new StudentResponse(
                        student.getId(),
                        student.getFullName(),
                        student.getMatricNumber(),
                        student.getEmail(),
                        student.getDepartment().getName(),
                        student.isBlocked()
                ))
                .toList();
    }

    /*
     * ENROLL STUDENT
     */
    public void enrollStudent(Long userId, Long courseId) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        boolean exists = enrollmentRepository
                .existsByStudentIdAndCourseId(student.getId(), courseId);

        if (exists) {
            throw new RuntimeException("Already enrolled");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        enrollmentRepository.save(enrollment);
    }

    /*
     * STUDENT DASHBOARD
     */
    public StudentDashboardResponse getStudentDashboard(Long userId) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudentId(student.getId());

        List<String> courses = enrollments
                .stream()
                .map(e ->
                        e.getCourse().getCode()
                                + " - "
                                + e.getCourse().getName()
                                + " - "
                                + e.getCourse().getLecturer().getFullName()
                )
                .toList();


        List<Long> courseIds = enrollments.stream()
                .map(e -> e.getCourse().getId())
                .toList();

        LocalDateTime now = LocalDateTime.now();


        List<AttendanceSession> sessions =
                attendanceSessionRepository.findActiveSessionsForCourses(courseIds);

        AttendanceSession validSession = sessions.stream()
                .filter(s -> s.isActive())
                .filter(s -> s.getStartedAt() != null)
                .filter(s -> now.isBefore(
                        s.getStartedAt().plusMinutes(s.getDurationMinutes())
                ))
                .findFirst()
                .orElse(null);
        /*
         * NO ACTIVE SESSION
         */
        if (validSession == null) {
            return new StudentDashboardResponse(
                    student.getFullName(),
                    student.getMatricNumber(),
                    null,   // sessionId
                    null,
                    null,
                    null,
                    null,
                    0,
                    courses,
                    false,
                    null,
                    0

            );
        }

        System.out.println("ACTIVE SESSION ID: " + validSession.getId());
        System.out.println(" \n \n  \n  Lecturer: " + validSession.getLecturer());

        AttendanceSession activeSession = validSession;

        boolean alreadyMarked = attendanceRecordRepository
                .existsBySessionIdAndStudentId(
                        activeSession.getId(),
                        student.getId()
                );
        /*
         * ACTIVE SESSION FOUND
         */
//        AttendanceSession activeSession = sessions.get(0);

        return new StudentDashboardResponse(
                student.getFullName(),
                student.getMatricNumber(),
                activeSession.getCourse().getId(),     // courseId ✅
                activeSession.getId(),                 // sessionId
                activeSession.getCourse().getCode(),
                activeSession.getCourse().getName(),
                activeSession.getCourse().getLecturer().getFullName(),
                (int) activeSession.getRadiusMeters(),
                courses,
                alreadyMarked ,
                activeSession.getStartedAt().toString(),   // 🔥 ADD
                activeSession.getDurationMinutes()         // 🔥 ADD
        );
    }

    /*
     * GET AVAILABLE COURSES
     */
    public List<Course> getAvailableCourses(Long userId) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getLevel() == null) {
            throw new RuntimeException("Student level not set");
        }

        List<Course> filteredCourses = courseRepository
                .findByDepartmentIdAndLevel(
                        student.getDepartment().getId(),
                        student.getLevel()
                );

        List<Enrollment> enrollments =
                enrollmentRepository.findByStudentId(student.getId());

        Set<Long> enrolledIds = enrollments.stream()
                .map(e -> e.getCourse().getId())
                .collect(Collectors.toSet());

        return filteredCourses.stream()
                .filter(c -> !enrolledIds.contains(c.getId()))
                .toList();
    }

    /*
     * ATTENDANCE HISTORY
     */
    public List<AttendanceHistoryResponse> getAttendanceHistory(Long userId) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<AttendanceRecord> records =
                attendanceRecordRepository.findByStudentIdOrderByMarkedAtDesc(student.getId());

        return records.stream()
                .map(r -> new AttendanceHistoryResponse(
                        r.getSession().getCourse().getCode(),
                        r.getSession().getCourse().getName(),
                        r.getStatus(),
                        r.getMarkedAt()
                ))
                .toList();
    }

    /*
     * STUDENT PROFILE
     */
    public StudentProfileResponse getStudentProfile(Long userId) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<AttendanceRecord> records =
                attendanceRecordRepository.findByStudentIdOrderByMarkedAtDesc(student.getId());

        long present = attendanceService.countPresent(student.getId());
        long late = attendanceService.countLate(student.getId());
        long absent = attendanceService.countAbsent(student.getId());
        return new StudentProfileResponse(

                student.getFullName(),
                student.getMatricNumber(),
                student.getEmail(),
                student.getDepartment().getName(),
                student.getLevel(),

                present,
                late,
                absent,

                student.getProfileImageUrl()
        );
    }

    /*
     * SAVE PROFILE IMAGE
     */
    public String saveProfileImage(Long userId, String imageUrl) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setProfileImageUrl(imageUrl);

        studentRepository.save(student);

        return "Profile image updated";
    }

    /*
     * UPLOAD PROFILE IMAGE
     */
    public String uploadProfileImage(Long userId, MultipartFile file) throws IOException {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        String uploadDir = System.getProperty("user.dir") + "/uploads/profile-images";

        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String fileName = userId + "_" + file.getOriginalFilename();

        File dest = new File(uploadDir + "/" + fileName);

        file.transferTo(dest);

        String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        String imageUrl = baseUrl + "/uploads/profile-images/" + fileName;

        student.setProfileImageUrl(imageUrl);
        studentRepository.save(student);

        return imageUrl;
    }

    /*
     * DELETE STUDENT
     */
    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        studentRepository.delete(student);
    }

    /*
     * BLOCK / UNBLOCK
     */
    public Student toggleBlockStudent(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        student.setBlocked(!student.isBlocked());
        return studentRepository.save(student);
    }

    /*
     * GET STUDENT BY ID
     */
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public Student updateStudent(Long id, CreateStudentRequest request) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setMatricNumber(request.getMatricNumber());

        if (request.getLevel() != null) {
            student.setLevel(request.getLevel());
        }

        return studentRepository.save(student);
    }


    public AttendanceRecord getLatestAttendance(Long userId) {

        Student student = studentRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return attendanceRecordRepository
                .findTopByStudentIdOrderByMarkedAtDesc(student.getId())
                .orElseThrow(() -> new RuntimeException("No attendance found"));
    }
}