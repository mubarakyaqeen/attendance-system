package com.attendance.attendance_system.controller.admin;

import com.attendance.attendance_system.config.SystemConfigService;
import com.attendance.attendance_system.dto.*;
import com.attendance.attendance_system.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminAttendanceController {

    private final AttendanceService attendanceService;

    public AdminAttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }


    /*
     * Dashboard statistics
     */
    @GetMapping("/dashboard")
    public DashboardResponse dashboardStats() {
        return attendanceService.getDashboardStats();
    }

    /*
     * All attendance sessions
     */
    @GetMapping("/attendance/sessions")
    public List<AttendanceSessionResponse> getSessions() {
        return attendanceService.getAllSessions();
    }

    /*
     * Live attendance sessions
     */
    @GetMapping("/attendance/live-sessions")
    public List<LiveSessionResponse> liveSessions() {
        return attendanceService.getLiveSessions();
    }

    /*
     * Block / Unblock registration
     */
    @PutMapping("/toggle-registration/{sessionId}")
    public boolean toggleRegistration(@PathVariable Long sessionId) {
        attendanceService.toggleRegistration(sessionId);
        return true;
    }
}