package com.attendance.attendance_system.service;

import com.attendance.attendance_system.dto.*;
import com.attendance.attendance_system.model.*;
import com.attendance.attendance_system.repository.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceSessionRepository attendanceSessionRepository;
    private final AttendanceRecordRepository attendanceRecordRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LecturerRepository lecturerRepository;
    private final CourseRepository courseRepository;

    public AttendanceService(
            AttendanceSessionRepository attendanceSessionRepository,
            AttendanceRecordRepository attendanceRecordRepository,
            StudentRepository studentRepository,
            EnrollmentRepository enrollmentRepository, LecturerRepository lecturerRepository, CourseRepository courseRepository ) {
        this.attendanceSessionRepository = attendanceSessionRepository;
        this.attendanceRecordRepository = attendanceRecordRepository;
        this.studentRepository = studentRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.lecturerRepository = lecturerRepository;
        this.courseRepository = courseRepository;
    }

    /*
     ============================================
     MARK ATTENDANCE (MAIN LOGIC)
     ============================================
     */
    public AttendanceRecord markAttendanceByCourse(
            MarkAttendanceByCourseRequest request
    ) {

        /*
         * 1. Get student
         */
        Student student = studentRepository.findByUserId(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        /*
         * 2. Find active session for the course
         */
        AttendanceSession session = attendanceSessionRepository
                .findByCourseIdAndActiveTrue(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("No active session"));

        /*
         * 3. Check if student already marked attendance
         */
        boolean alreadyMarked =
                attendanceRecordRepository.existsBySessionIdAndStudentId(
                        session.getId(),
                        student.getId()
                );

        if (alreadyMarked) {
            throw new RuntimeException("Attendance already marked");
        }

        /*
         * 4. Validate GEO location (distance check)
         */
        double distance = calculateDistanceMeters(
                request.getLatitude(),
                request.getLongitude(),
                session.getLatitude(),
                session.getLongitude()
        );

        if (distance > session.getRadiusMeters()) {
            throw new RuntimeException("You are outside attendance radius");
        }

        /*
         * 5. Determine attendance status (PRESENT / LATE)
         */
        String status = determineAttendanceStatus(session);

        /*
         * 6. Save attendance record
         */
        AttendanceRecord record = new AttendanceRecord();
        record.setSession(session);
        record.setStudent(student);
        record.setStatus(status);

        return attendanceRecordRepository.save(record);
    }


    /*
     ============================================
     DETERMINE ATTENDANCE STATUS
     ============================================
     */
    private String determineAttendanceStatus(AttendanceSession session) {

        LocalDateTime now = LocalDateTime.now();

        LocalDateTime start = session.getStartedAt();

        int graceMinutes = session.getGracePeriodMinutes();

        LocalDateTime graceEnd = start.plusMinutes(graceMinutes);
        LocalDateTime end = start.plusMinutes(session.getDurationMinutes());

        if (now.isBefore(graceEnd) || now.isEqual(graceEnd)) {
            return "PRESENT";
        } else if (now.isAfter(graceEnd) && now.isBefore(end)) {
            return "LATE";
        } else {
            throw new RuntimeException("Attendance session has ended");
        }
    }


    /*
     ============================================
     CLOSE SESSION + MARK ABSENT
     ============================================
     */
    public void closeSessionAndMarkAbsent(Long sessionId) {

        AttendanceSession session = attendanceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        /*
         * Get all enrolled students for this course
         */
        List<Enrollment> enrollments =
                enrollmentRepository.findByCourseId(session.getCourse().getId());

        for (Enrollment e : enrollments) {

            Student student = e.getStudent();

            boolean exists = attendanceRecordRepository
                    .existsBySessionIdAndStudentId(
                            session.getId(),
                            student.getId()
                    );

            /*
             * If student didn't mark attendance → ABSENT
             */
            if (!exists) {

                AttendanceRecord record = new AttendanceRecord();
                record.setSession(session);
                record.setStudent(student);
                record.setStatus("ABSENT");

                attendanceRecordRepository.save(record);
            }
        }

        /*
         * Close session
         */
        session.setActive(false);
        session.setEndedAt(LocalDateTime.now());

        attendanceSessionRepository.save(session);
    }


    /*
     ============================================
     DISTANCE CALCULATION (HAVERSINE)
     ============================================
     */
    private double calculateDistanceMeters(
            double lat1,
            double lon1,
            double lat2,
            double lon2
    ) {

        final int R = 6371000; // Earth radius in meters

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    /*
=====================================
NEW METHODS FOR STUDENT PROFILE
=====================================
*/

    public long countPresent(Long studentId) {
        return attendanceRecordRepository
                .countByStudentIdAndStatus(studentId, "PRESENT");
    }

    public long countLate(Long studentId) {
        return attendanceRecordRepository
                .countByStudentIdAndStatus(studentId, "LATE");
    }

    public long countAbsent(Long studentId) {
        return attendanceRecordRepository
                .countByStudentIdAndStatus(studentId, "ABSENT");
    }

    /*
=====================================
ADMIN REPORT SUMMARY (RESTORED)
=====================================
*/
    public AttendanceReportSummary getSummary(String filter) {

        List<AttendanceRecord> records = attendanceRecordRepository.findAll();

        records = filterRecords(records, filter);

        long total = records.size();

        long present = records.stream()
                .filter(r -> "PRESENT".equals(r.getStatus()))
                .count();

        long late = records.stream()
                .filter(r -> "LATE".equals(r.getStatus()))
                .count();

        long absent = records.stream()
                .filter(r -> "ABSENT".equals(r.getStatus()))
                .count();

        return new AttendanceReportSummary(total, present, late, absent);
    }


    /*
    =====================================
    ATTENDANCE TREND (RESTORED)
    =====================================
    */
    public List<AttendanceTrendResponse> getTrend(String filter) {

        List<AttendanceRecord> records = attendanceRecordRepository.findAll();

        records = filterRecords(records, filter);

        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getMarkedAt().toLocalDate(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .map(e -> new AttendanceTrendResponse(
                        e.getKey().toString(),
                        e.getValue()
                ))
                .toList();
    }

    private List<AttendanceRecord> filterRecords(List<AttendanceRecord> records, String filter) {

        LocalDate today = LocalDate.now();

        return records.stream().filter(r -> {

            LocalDate date = r.getMarkedAt().toLocalDate();

            return switch (filter) {
                case "TODAY" -> date.equals(today);
                case "WEEK" -> date.isAfter(today.minusDays(7));
                case "MONTH" -> date.isAfter(today.minusDays(30));
                default -> true;
            };

        }).toList();
    }


    /*
=====================================
ADMIN DASHBOARD STATS
=====================================
*/
    public DashboardResponse getDashboardStats() {

        long totalStudents = studentRepository.count();
        long totalLecturers = lecturerRepository.count();
        long totalCourses = courseRepository.count();
        long totalSessions = attendanceSessionRepository.count();

        return new DashboardResponse(
                "Admin Dashboard",
                totalStudents,
                totalLecturers,
                totalCourses,
                totalSessions
        );
    }

    /*
    =====================================
    GET ALL SESSIONS
    =====================================
    */
    public List<AttendanceSessionResponse> getAllSessions() {

        return attendanceSessionRepository.findAll()
                .stream()
                .map(session -> {

                    long present = attendanceRecordRepository
                            .findBySessionId(session.getId())
                            .stream()
                            .filter(r -> "PRESENT".equals(r.getStatus()))
                            .count();

                    long late = attendanceRecordRepository
                            .findBySessionId(session.getId())
                            .stream()
                            .filter(r -> "LATE".equals(r.getStatus()))
                            .count();

                    long absent = attendanceRecordRepository
                            .findBySessionId(session.getId())
                            .stream()
                            .filter(r -> "ABSENT".equals(r.getStatus()))
                            .count();

                    // ✅ SAFE NULL HANDLING
                    String courseCode = session.getCourse() != null
                            ? session.getCourse().getCode()
                            : "Unknown";

                    String courseName = session.getCourse() != null
                            ? session.getCourse().getName()
                            : "N/A";

                    String lecturerName = session.getLecturer() != null
                            ? session.getLecturer().getFullName()
                            : "N/A";

                    String sessionDate = session.getSessionDate() != null
                            ? session.getSessionDate().toString()
                            : "";

                    return new AttendanceSessionResponse(
                            session.getId(),
                            courseCode,
                            courseName,
                            lecturerName,
                            sessionDate,
                            present,
                            late,
                            absent,
                            session.isActive()
                    );
                })
                .toList();
    }

    /*
    =====================================
    GET LIVE SESSIONS
    =====================================
    */
    public List<LiveSessionResponse> getLiveSessions() {

        return attendanceSessionRepository.findByActiveTrue()
                .stream()
                .map(session -> {

                    long present = attendanceRecordRepository
                            .findBySessionId(session.getId())
                            .stream()
                            .filter(r -> "PRESENT".equals(r.getStatus()))
                            .count();

                    long late = attendanceRecordRepository
                            .findBySessionId(session.getId())
                            .stream()
                            .filter(r -> "LATE".equals(r.getStatus()))
                            .count();

                    long absent = attendanceRecordRepository
                            .findBySessionId(session.getId())
                            .stream()
                            .filter(r -> "ABSENT".equals(r.getStatus()))
                            .count();

                    // ✅ SAFE NULL HANDLING
                    String courseText = session.getCourse() != null
                            ? session.getCourse().getCode() + " - " + session.getCourse().getName()
                            : "Unknown Course";

                    String lecturerName = session.getLecturer() != null
                            ? session.getLecturer().getFullName()
                            : "N/A";

                    return new LiveSessionResponse(
                            courseText,
                            lecturerName,
                            present,
                            late,
                            absent
                    );
                })
                .toList();
    }

    /*
    =====================================
    TOGGLE REGISTRATION
    =====================================
    */
    public void toggleRegistration(Long sessionId) {

        AttendanceSession session = attendanceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        session.setActive(!session.isActive());

        attendanceSessionRepository.save(session);
    }

    /*
    =====================================
    START SESSION
    =====================================
    */
    public AttendanceSession startSession(StartSessionRequest request) {

        AttendanceSession session = new AttendanceSession();

        // ✅ SET LECTURER (THIS FIXES YOUR BUG)
        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        session.setLecturer(lecturer);

        // ✅ SET COURSE (you likely already have this)
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        session.setCourse(course);

        // ✅ VERY IMPORTANT
        session.setActive(true);

        session.setStartedAt(LocalDateTime.now());
        session.setDurationMinutes(request.getDurationMinutes());

        return attendanceSessionRepository.save(session);
    }

    /*
    =====================================
    END SESSION
    =====================================
    */
    public void endSession(Long sessionId) {

        // 🔥 This method already:
        // - marks absent students
        // - sets session inactive
        // - saves everything
        closeSessionAndMarkAbsent(sessionId);
    }

    /*
    =====================================
    GET SESSION ATTENDANCE
    =====================================
    */
    public List<AttendanceRecord> getSessionAttendance(Long sessionId) {
        return attendanceRecordRepository.findBySessionId(sessionId);
    }

    /*
    =====================================
    ACTIVE SESSION FOR LECTURER
    =====================================
    */
    public Optional<AttendanceSession> getActiveSessionForLecturer(Long lecturerId) {
        return attendanceSessionRepository
                .findFirstByLecturerIdAndActiveTrue(lecturerId);
    }

    /*
    =====================================
    LECTURER SUMMARY
    =====================================
    */
    public List<CourseAttendanceSummary> getLecturerAttendanceSummary(Long lecturerId) {

        List<Object[]> results =
                attendanceRecordRepository.getCourseAttendanceSummary(lecturerId);

        return results.stream()
                .map(row -> new CourseAttendanceSummary(
                        (Long) row[0],                     // courseId
                        (String) row[1],                   // code
                        (String) row[2],                   // name
                        ((Number) row[3]).longValue(),     // totalSessions
                        ((Number) row[4]).doubleValue()    // averageAttendance ✅
                ))
                .toList();
    }



    public AttendanceRecord markAttendanceBySession(
            Long studentId,
            Long sessionId,
            double latitude,
            double longitude
    ) {

        System.out.println("REQUEST SESSION ID: " + sessionId);

        // 1. Get student
        Student student = studentRepository.findByUserId(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        // 2. Get session directly (🔥 FIX)
        AttendanceSession session = attendanceSessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        // 3. Check if session is active

//        if (!session.isActive()) {
//            throw new RuntimeException("Session is not active");
//        }
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endTime = session.getStartedAt()
                .plusMinutes(session.getDurationMinutes());

        if (!session.isActive() || now.isAfter(endTime)) {

            //
            if (session.isActive() && now.isAfter(endTime)) {
                closeSessionAndMarkAbsent(session.getId());
            }

            // 🔥 auto close session if expired
            if (session.isActive()) {
                session.setActive(false);
                session.setEndedAt(now);
                attendanceSessionRepository.save(session);
            }

            throw new RuntimeException("Session is not active");
        }

        // 4. Prevent duplicate
        boolean alreadyMarked =
                attendanceRecordRepository.existsBySessionIdAndStudentId(
                        sessionId,
                        studentId
                );

        if (alreadyMarked) {
            throw new RuntimeException("Attendance already marked");
        }

        // 5. Check distance
        double distance = calculateDistanceMeters(
                latitude,
                longitude,
                session.getLatitude(),
                session.getLongitude()
        );

        if (distance > session.getRadiusMeters()) {
            throw new RuntimeException("Outside allowed radius");
        }

        // 6. Determine status (reuse your method)
        String status = determineAttendanceStatus(session);

        // 7. Save
        AttendanceRecord record = new AttendanceRecord();
        record.setSession(session);
        record.setStudent(student);
        record.setStatus(status);

        System.out.println("Session ID from request: " + sessionId);

        return attendanceRecordRepository.save(record);
    }

    @Scheduled(fixedRate = 3000) // every 3 seconds
    public void autoCloseExpiredSessions() {

        List<AttendanceSession> activeSessions =
                attendanceSessionRepository.findByActiveTrue();

        LocalDateTime now = LocalDateTime.now();

        for (AttendanceSession session : activeSessions) {

            if (session.getStartedAt() == null) continue;

            LocalDateTime endTime = session.getStartedAt()
                    .plusMinutes(session.getDurationMinutes());

            if (now.isAfter(endTime)) {

                System.out.println("AUTO-CLOSING SESSION: " + session.getId());

                closeSessionAndMarkAbsent(session.getId());
            }
        }
    }


}