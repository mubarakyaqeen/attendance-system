package com.attendance.attendance_system.controller.admin;

import com.attendance.attendance_system.model.Department;
import com.attendance.attendance_system.service.DepartmentService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * REST Controller exposes Department APIs
 */

@RestController
@RequestMapping("/admin/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    /*
     * Create a new department
     */
    @PostMapping
    public Department createDepartment(@RequestBody Department department) {
        return departmentService.createDepartment(department);
    }

    /*
     * Get all departments
     */
    @GetMapping
    public List<Department> getDepartments() {
        return departmentService.getAllDepartments();
    }

    /*
     * Delete department
     */
    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        departmentService.deleteDepartment(id);
    }

}