package com.attendance.attendance_system.service.lecturer;

import com.attendance.attendance_system.model.Course;
import com.attendance.attendance_system.model.Lecturer;
import com.attendance.attendance_system.repository.CourseRepository;
import com.attendance.attendance_system.repository.LecturerRepository;
import org.springframework.stereotype.Service;

@Service
public class LecturerCourseService {

    private final CourseRepository courseRepository;
    private final LecturerRepository lecturerRepository;

    public LecturerCourseService(
            CourseRepository courseRepository,
            LecturerRepository lecturerRepository) {
        this.courseRepository = courseRepository;
        this.lecturerRepository = lecturerRepository;
    }

    public Course createCourseForLecturer(
            Long userId,
            String courseCode,
            String courseTitle
    ) {

        Lecturer lecturer = lecturerRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Lecturer not found"));

        Course course = new Course();
        course.setCode(courseCode);
        course.setName(courseTitle);
        course.setLecturer(lecturer);

        return courseRepository.save(course);
    }
}