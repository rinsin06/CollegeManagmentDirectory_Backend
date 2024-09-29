package com.collegedirectory.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@NoArgsConstructor
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id")
    private Long studentId; // Foreign key to StudentProfile

    @Column(name = "course_id")
    private Long departmentId; // Foreign key to Course

    public Enrollment(Long studentId, Long courseId) {
        this.studentId = studentId;
        this.departmentId = courseId;
    }
}
