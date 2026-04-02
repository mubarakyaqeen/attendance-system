package com.attendance.attendance_system.service.report;

import com.attendance.attendance_system.dto.report.AdminDashboardResponse;
import com.attendance.attendance_system.repository.*;
import org.springframework.stereotype.Service;

/*
 * Service responsible for dashboard statistics
 */

@Service
public class DashboardService {

    private final StudentRepository studentRepo;
    private final LecturerRepository lecturerRepo;
    private final CourseRepository courseRepo;
    private final DepartmentRepository departmentRepo;
    private final AttendanceRecordRepository recordRepo;

    public DashboardService(
            StudentRepository studentRepo,
            LecturerRepository lecturerRepo,
            CourseRepository courseRepo,
            DepartmentRepository departmentRepo,
            AttendanceRecordRepository recordRepo
    ) {
        this.studentRepo = studentRepo;
        this.lecturerRepo = lecturerRepo;
        this.courseRepo = courseRepo;
        this.departmentRepo = departmentRepo;
        this.recordRepo = recordRepo;
    }

    /*
     * Build admin dashboard statistics
     */
    public AdminDashboardResponse getAdminDashboard() {

        long students = studentRepo.count();
        long lecturers = lecturerRepo.count();
        long courses = courseRepo.count();
        long departments = departmentRepo.count();

        long totalAttendance = recordRepo.count();

        long present =
                recordRepo.countByStatus("PRESENT");

        double rate = 0;

        if (totalAttendance > 0) {
            rate = ((double) present / totalAttendance) * 100;
        }

        return new AdminDashboardResponse(
                students,
                lecturers,
                courses,
                departments,
                rate
        );
    }
}