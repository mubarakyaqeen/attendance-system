package com.attendance.attendance_system.service;

import com.attendance.attendance_system.model.Semester;
import com.attendance.attendance_system.repository.SemesterRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Business logic for Semester operations
 */

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;

    /*
     * Create new semester
     */
    public Semester createSemester(Semester semester) {
        return semesterRepository.save(semester);
    }

    /*
     * Get all semesters
     */
    public List<Semester> getAllSemesters() {
        return semesterRepository.findAll();
    }

    /*
     * Delete semester
     */
    public void deleteSemester(Long id) {
        semesterRepository.deleteById(id);
    }

}