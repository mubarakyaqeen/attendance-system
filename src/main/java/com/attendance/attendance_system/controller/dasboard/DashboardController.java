package com.attendance.attendance_system.controller.dasboard;

import com.attendance.attendance_system.dto.report.AdminDashboardResponse;
import com.attendance.attendance_system.service.report.DashboardService;
import org.springframework.web.bind.annotation.*;

/*
 * Dashboard APIs
 */

@RestController
@RequestMapping("/dashboard")

public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /*
     * Admin dashboard
     */
    @GetMapping("/admin")
    public AdminDashboardResponse adminDashboard() {
        return dashboardService.getAdminDashboard();
    }
}