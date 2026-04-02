package com.attendance.attendance_system.controller.lecturer;

import com.attendance.attendance_system.dto.StartSessionRequest;
import com.attendance.attendance_system.model.AttendanceRecord;
import com.attendance.attendance_system.model.AttendanceSession;
import com.attendance.attendance_system.service.AttendanceService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/*
 * Lecturer attendance management endpoints
 * Handles:
 * - Start attendance session
 * - Stop attendance session
 * - View attendance records
 * - Fetch active session
 */

@RestController
@RequestMapping("/lecturer")
public class LecturerAttendanceController {

    private final AttendanceService attendanceService;

    public LecturerAttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /*
     * ============================================
     * START ATTENDANCE SESSION
     * ============================================
     */

    @PostMapping("/attendance/start-session")
    public ResponseEntity<?> startSession(
            @RequestBody StartSessionRequest request
    ) {

        AttendanceSession session =
                attendanceService.startSession(request);

        return ResponseEntity.ok(session);
    }

    /*
     * ============================================
     * STOP SESSION MANUALLY
     * ============================================
     */

    @PostMapping("/attendance/stop-session/{sessionId}")
    public ResponseEntity<?> stopSession(
            @PathVariable Long sessionId
    ) {

        attendanceService.endSession(sessionId);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Session stopped successfully");

        return ResponseEntity.ok(response);
    }

    /*
     * ============================================
     * GET ATTENDANCE RECORDS FOR SESSION
     * ============================================
     */

    @GetMapping("/attendance/session/{sessionId}/records")
    public ResponseEntity<List<AttendanceRecord>> getSessionAttendance(
            @PathVariable Long sessionId
    ) {

        List<AttendanceRecord> records =
                attendanceService.getSessionAttendance(sessionId);

        return ResponseEntity.ok(records);
    }

    /*
     * ============================================
     * GET ACTIVE SESSION FOR LECTURER
     * ============================================
     */

    @GetMapping("/{lecturerId}/active-session")
    public ResponseEntity<?> getActiveSession(
            @PathVariable Long lecturerId
    ) {

        Optional<AttendanceSession> session =
                attendanceService.getActiveSessionForLecturer(lecturerId);

        if (session.isEmpty()) {
            return ResponseEntity.ok(null);
        }

        AttendanceSession s = session.get();

        Map<String, Object> response = new HashMap<>();

        response.put("sessionId", s.getId());
        response.put("courseCode", s.getCourse().getCode());
        response.put("courseName", s.getCourse().getName());
        response.put("courseId", s.getCourse().getId());
        response.put("startTime", s.getStartedAt());
        response.put("duration", s.getDurationMinutes());

        return ResponseEntity.ok(response);
    }

}