package com.attendance.attendance_system.service;

import com.attendance.attendance_system.dto.CreateCourseRequest;
import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.model.Department;
import com.attendance.attendance_system.model.Lecturer;
import com.attendance.attendance_system.repository.CourseRepository;
import com.attendance.attendance_system.repository.DepartmentRepository;
import com.attendance.attendance_system.repository.LecturerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * Business logic for courses
 */

@Service
public class CourseService {

    private final CourseRepository courseRepository;
    private final DepartmentRepository departmentRepository;
    private final LecturerRepository lecturerRepository;

    public CourseService(
            CourseRepository courseRepository,
            DepartmentRepository departmentRepository,
            LecturerRepository lecturerRepository
    ) {
        this.courseRepository = courseRepository;
        this.departmentRepository = departmentRepository;
        this.lecturerRepository = lecturerRepository;
    }

    /*
     * Create a new course
     */
    public Course createCourse(CreateCourseRequest request) {

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Lecturer lecturer = lecturerRepository
                .findById(request.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        Course course = new Course();

        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setDepartment(department);
        course.setLecturer(lecturer);
        course.setLevel(request.getLevel());

        return courseRepository.save(course);
    }

    /*
     * Get all courses
     */
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    /*
     * Get course by ID
     */
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    /*
     * Update course
     */
    public Course updateCourse(Long id, CreateCourseRequest request) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Department not found"));

        Lecturer lecturer = lecturerRepository.findById(request.getLecturerId())
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        course.setCode(request.getCode());
        course.setName(request.getName());
        course.setDepartment(department);
        course.setLecturer(lecturer);

        return courseRepository.save(course);
    }

    /*
     * Delete course
     */
    public void deleteCourse(Long id) {

        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        courseRepository.delete(course);
    }

    /*
     * Get courses created by lecturer
     */

    public List<Course> getCoursesByLecturer(Long lecturerId){

        return courseRepository.findByLecturerId(lecturerId);

    }


}
