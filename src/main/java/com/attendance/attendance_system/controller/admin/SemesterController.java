package com.attendance.attendance_system.controller.admin;

import com.attendance.attendance_system.model.Semester;
import com.attendance.attendance_system.service.SemesterService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * REST controller for semester endpoints
 */

@RestController
@RequestMapping("/admin/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    /*
     * Create semester
     */
    @PostMapping
    public Semester createSemester(@RequestBody Semester semester) {
        return semesterService.createSemester(semester);
    }

    /*
     * Get all semesters
     */
    @GetMapping
    public List<Semester> getSemesters() {
        return semesterService.getAllSemesters();
    }

    /*
     * Delete semester
     */
    @DeleteMapping("/{id}")
    public void deleteSemester(@PathVariable Long id) {
        semesterService.deleteSemester(id);
    }

}