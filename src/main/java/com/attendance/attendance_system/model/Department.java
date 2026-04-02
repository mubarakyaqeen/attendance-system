package com.attendance.attendance_system.model;


import jakarta.persistence.*;
import lombok.*;

/*
 * Department entity represents academic departments
 * like Computer Science, Electrical Engineering, etc.
 */
@Entity
@Table(name = "departments")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Maps to department_name column in database
     */
    @Column(name = "department_name", nullable = false, unique = true)
    private String name;



}