package com.attendance.attendance_system.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

/*
 * Lecturer entity
 * Stores lecturer academic information
 */

@Entity
@Table(name = "lecturers")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Lecturer {

    /*
     * Primary key
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * Lecturer full name
     */
    @Column(nullable = false)
    private String fullName;

    /*
     * Lecturer email
     */
    @Column(nullable = false, unique = true)
    private String email;

    /*
     * Department relationship
     */
    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    /*
     * Link lecturer to login user
     */
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /*
     * Creation timestamp
     */
    private LocalDateTime createdAt = LocalDateTime.now();

}