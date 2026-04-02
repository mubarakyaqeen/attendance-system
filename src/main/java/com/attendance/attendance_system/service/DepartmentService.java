package com.attendance.attendance_system.service;

import com.attendance.attendance_system.model.Department;
import com.attendance.attendance_system.repository.DepartmentRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Service layer contains business logic
 */

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    /*
     * Create new department
     */
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    /*
     * Get all departments
     */
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    /*
     * Delete department
     */
    public void deleteDepartment(Long id) {
        departmentRepository.deleteById(id);
    }

}