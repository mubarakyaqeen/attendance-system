package com.attendance.attendance_system.controller.lecturer;

import com.attendance.attendance_system.dto.CourseAttendanceSummary;
import com.attendance.attendance_system.dto.CreateLecturerRequest;
import com.attendance.attendance_system.dto.LecturerResponse;
import com.attendance.attendance_system.model.Lecturer;
import com.attendance.attendance_system.service.AttendanceService;
import com.attendance.attendance_system.service.LecturerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/lecturers")
public class LecturerController {

    private final LecturerService lecturerService;
    private final AttendanceService attendanceService ;

    public LecturerController(LecturerService lecturerService, AttendanceService attendanceService) {
        this.lecturerService = lecturerService;
        this.attendanceService = attendanceService;
    }

    /*
     * Create Lecturer
     */
    @PostMapping
    public ResponseEntity<LecturerResponse> createLecturer(
            @Valid @RequestBody CreateLecturerRequest request
    ) {

        Lecturer lecturer = lecturerService.createLecturer(request);

        LecturerResponse response = mapToResponse(lecturer);

        return ResponseEntity.ok(response);
    }

    /*
     * Get all lecturers
     */
    @GetMapping
    public ResponseEntity<List<LecturerResponse>> getAllLecturers() {

        List<LecturerResponse> lecturers = lecturerService.getAllLecturers()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(lecturers);
    }

    /*
     * Get lecturer by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<LecturerResponse> getLecturerById(
            @PathVariable Long id
    ) {

        Lecturer lecturer = lecturerService.getLecturerById(id);

        return ResponseEntity.ok(mapToResponse(lecturer));
    }

    /*
     * Update lecturer
     */
    @PutMapping("/{id}")
    public ResponseEntity<LecturerResponse> updateLecturer(
            @PathVariable Long id,
            @Valid @RequestBody CreateLecturerRequest request
    ) {

        Lecturer lecturer = lecturerService.updateLecturer(id, request);

        return ResponseEntity.ok(mapToResponse(lecturer));
    }

    /*
     * Delete lecturer
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLecturer(@PathVariable Long id) {

        lecturerService.deleteLecturer(id);

        return ResponseEntity.ok("Lecturer deleted successfully");
    }

    /*
     * Activate / Deactivate lecturer
     * Useful for blocking lecturer accounts
     */
//    @PatchMapping("/{id}/toggle-status")
//    public ResponseEntity<String> toggleLecturerStatus(@PathVariable Long id) {
//
//        lecturerService.toggleLecturerStatus(id);
//
//        return ResponseEntity.ok("Lecturer status updated");
//    }

    /*
     * Helper method to map Lecturer → LecturerResponse
     */
    private LecturerResponse mapToResponse(Lecturer lecturer) {

        return new LecturerResponse(
                lecturer.getId(),
                lecturer.getFullName(),
                lecturer.getEmail(),
                lecturer.getDepartment() != null
                        ? lecturer.getDepartment().getName()
                        : "No Department"
        );
    }

    @GetMapping("/{lecturerId}/attendance-summary")
    public List<CourseAttendanceSummary> getAttendanceSummary(
            @PathVariable Long lecturerId
    ) {
        return attendanceService.getLecturerAttendanceSummary(lecturerId);
    }
}