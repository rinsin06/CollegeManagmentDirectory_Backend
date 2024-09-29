package com.collegedirectory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collegedirectory.entities.FacultyProfile;
import com.collegedirectory.entities.StudentProfile;
import com.collegedirectory.exception.InvalidDataException;
import com.collegedirectory.exception.ResourceNotFoundException;
import com.collegedirectory.respositories.FacultyProfileRepository;
import com.collegedirectory.services.AdminService;
import com.collegedirectory.services.FacultyService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private FacultyProfileRepository  faculityProfileRepository;
    
    @Autowired
    private FacultyService facultyService;

    @GetMapping("/students")
    public ResponseEntity<List<StudentProfile>> getAllStudents() {
    	
    	List<StudentProfile> students = adminService.getAllStudents();
        if (students.isEmpty()) {
            throw new ResourceNotFoundException("No students found");
        }
        return ResponseEntity.ok(students);
    }

    @PostMapping("/students")
    public ResponseEntity<StudentProfile> addStudent(@RequestBody StudentProfile studentProfile) {
    	 if (studentProfile.getUser() == null) {
             throw new InvalidDataException("User cannot be null");
         }
        StudentProfile createdStudent = adminService.addStudent(studentProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudent);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<StudentProfile> updateStudent(@PathVariable Long id, @RequestBody StudentProfile studentProfile) {
        return adminService.updateStudent(id, studentProfile)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResourceNotFoundException("Student with ID " + id + " not found"));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
    	if (!adminService.studentExists(id)) {
            throw new ResourceNotFoundException("Student with ID " + id + " not found");
        }
        adminService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/faculty")
    public ResponseEntity<List<FacultyProfile>> getAllFaculty() {
    	List<FacultyProfile> faculty = adminService.getAllFaculty();
        if (faculty.isEmpty()) {
            throw new ResourceNotFoundException("No faculty found");
        }
        return ResponseEntity.ok(faculty);
    }

    @PostMapping("/faculty")
    public ResponseEntity<FacultyProfile> addFaculty(@RequestBody FacultyProfile facultyProfile) {
    	if (facultyProfile.getUser() == null) {
            throw new InvalidDataException("User cannot be null");
        }
        FacultyProfile createdFaculty = adminService.addFaculty(facultyProfile);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFaculty);
    }

    public Optional<FacultyProfile> updateFaculty(Long id, FacultyProfile facultyProfile) {
        if (faculityProfileRepository.existsById(id)) {
            facultyProfile.setUserId(id); 
            return Optional.of(faculityProfileRepository.save(facultyProfile)); 
        }else {
        	
        	throw new ResourceNotFoundException("Faculty with ID " + id + " not found");
        	 
        }
 
    }

    @DeleteMapping("/faculty/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
    	 if (!faculityProfileRepository.existsById(id)) {
             throw new ResourceNotFoundException("Faculty with ID " + id + " not found");
         }
        adminService.deleteFaculty(id);
        return ResponseEntity.noContent().build();
    }
    
    @PostMapping("/faculty/{facultyId}/assign-courses")
    public ResponseEntity<String> assignCoursesToFaculty(
            @PathVariable Long facultyId,
            @RequestBody List<Long> courseIds) {
        try {
        	adminService.assignCoursesToFaculty(facultyId, courseIds);
            return ResponseEntity.ok("Courses assigned successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Faculty or Course not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred: " + e.getMessage());
        }
    }

//    @GetMapping("/enrollment-trends")
//    public ResponseEntity<?> getEnrollmentTrends(@RequestParam Optional<Integer> year) {
//        return ResponseEntity.ok(enrollmentService.getEnrollmentTrends(year.orElse(2024)));
//    }
//
//    @GetMapping("/faculty-course-load")
//    public ResponseEntity<?> getFacultyCourseLoad(@RequestParam Optional<String> department) {
//        return ResponseEntity.ok(facultyService.getFacultyCourseLoad(department.orElse("All")));
//    }
}
