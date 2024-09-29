package com.collegedirectory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collegedirectory.entities.FacultyProfile;
import com.collegedirectory.entities.StudentProfile;
import com.collegedirectory.entities.User;
import com.collegedirectory.services.FacultyService;
import com.collegedirectory.services.StudentService;
import com.collegedirectory.services.UserService;

import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/faculty")
public class FacultyController {

    @Autowired
    private FacultyService facultyProfileService;
    
    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService; 

    @GetMapping("/profile")
    public ResponseEntity<FacultyProfile> viewProfile(@AuthenticationPrincipal UserDetails userDetails) {
       
        User user = userService.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        
        return facultyProfileService.findByUser(user)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    
   
    @GetMapping("/classlist")
    public ResponseEntity<List<StudentProfile>>  manageClassList(@AuthenticationPrincipal UserDetails userDetails) {
    	User user = userService.findByUsername(userDetails.getUsername()).get();
    	 FacultyProfile faculty = facultyProfileService.findById(user.getId())
    	         .orElseThrow(() -> new EntityNotFoundException("Faculty profile not found"));
    	
    	 List<StudentProfile> students = studentService.getAllStudentsByDepartmentId(faculty.getDepartmentId());
               
    	 
    	 return ResponseEntity.ok(students);
    
    }

   
    @PutMapping("/profile")
    public ResponseEntity<FacultyProfile> updateProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                        @RequestBody FacultyProfile updatedProfile) {
    	
    	 Long userId = userService.findByUsername(userDetails.getUsername())
                 .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                 .getId();
        return facultyProfileService.updateFaculty(userId, updatedProfile)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Unable to update profile"));
    }
}


