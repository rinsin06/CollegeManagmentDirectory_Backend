package com.collegedirectory.entities;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "course")
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(name = "department_id")
    private Long departmentId; // Foreign key to Department

//    @Column(name = "faculty_id")
//    private Long facultyId; // Foreign key to FacultyProfile
    
    @ManyToMany(mappedBy = "courses")
    private Set<FacultyProfile> facultyMembers = new HashSet<>();

    public Course(String title, String description, Long departmentId, Long facultyId) {
        this.title = title;
        this.description = description;
        this.departmentId = departmentId;
        
    }
}

