package com.attendance.attendance_system.controller.student;

import com.attendance.attendance_system.dto.MarkAttendanceByCourseRequest;
import com.attendance.attendance_system.dto.MarkAttendanceRequestV2;
import com.attendance.attendance_system.model.AttendanceRecord;
import com.attendance.attendance_system.model.AttendanceSession;
import com.attendance.attendance_system.repository.AttendanceSessionRepository;
import com.attendance.attendance_system.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/*
 * Student attendance endpoints
 */

@RestController
@RequestMapping("/student")

public class StudentAttendanceController {

    private final AttendanceService attendanceService;
    private final AttendanceSessionRepository attendanceSessionRepository ;

    public StudentAttendanceController(AttendanceService attendanceService, AttendanceSessionRepository attendanceSessionRepository) {
        this.attendanceService = attendanceService;
        this.attendanceSessionRepository = attendanceSessionRepository;
    }



    @PostMapping("/mark-attendance")
    public AttendanceRecord markAttendanceByCourse(
            @RequestBody MarkAttendanceByCourseRequest request
    ) {
        return attendanceService.markAttendanceByCourse(request);
    }

//    @PostMapping("/mark-attendance-v2")
//    public AttendanceRecord markAttendanceV2(
//            @RequestBody MarkAttendanceRequestV2 request
//    ) {
//        return attendanceService.markAttendanceV2(request);
//    }

    @PostMapping("/mark-attendance-v2")
    public AttendanceRecord markAttendanceV2(@RequestBody Map<String, Object> body) {

        Long studentId = Long.valueOf(body.get("studentId").toString());
        Long sessionId = Long.valueOf(body.get("sessionId").toString());
        double latitude = Double.parseDouble(body.get("latitude").toString());
        double longitude = Double.parseDouble(body.get("longitude").toString());

        return attendanceService.markAttendanceBySession(
                studentId,
                sessionId,
                latitude,
                longitude
        );
    }


    @GetMapping("/active-session")
    public AttendanceSession getActiveSession(@RequestParam Long courseId) {

        return attendanceSessionRepository
                .findByCourseIdAndActiveTrue(courseId)
                .orElseThrow(() -> new RuntimeException("No active session"));
    }

}