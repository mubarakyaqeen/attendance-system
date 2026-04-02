package com.attendance.attendance_system.controller.admin;

import com.attendance.attendance_system.dto.AttendanceReportSummary;
import com.attendance.attendance_system.dto.AttendanceTrendResponse;
import com.attendance.attendance_system.service.AttendanceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reports")

public class AdminReportController {

    private final AttendanceService attendanceService;

    public AdminReportController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping("/summary")
    public AttendanceReportSummary getSummary(
            @RequestParam(required = false, defaultValue = "ALL") String filter
    ) {
        return attendanceService.getSummary(filter);
    }

    @GetMapping("/trend")
    public List<AttendanceTrendResponse> getTrend(
            @RequestParam(required = false, defaultValue = "ALL") String filter
    ) {
        return attendanceService.getTrend(filter);
    }

//    @GetMapping("/summary")
//    public AttendanceReportSummary getSummary() {
//        return attendanceService.getAttendanceReportSummary();
//    }
//
//    @GetMapping("/trend")
//    public List<AttendanceTrendResponse> getTrend() {
//        return attendanceService.getAttendanceTrend();
//    }
}