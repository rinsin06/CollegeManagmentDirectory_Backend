package com.collegedirectory.services;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.collegedirectory.dto.StudentRequest;
import com.collegedirectory.entities.Course;
import com.collegedirectory.entities.FacultyProfile;
import com.collegedirectory.entities.StudentProfile;
import com.collegedirectory.entities.User;
import com.collegedirectory.respositories.CourseRepository;
import com.collegedirectory.respositories.FacultyProfileRepository;
import com.collegedirectory.respositories.StudentProfileRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AdminService {
	
	 @Autowired
	    private StudentProfileRepository studentProfileRepository;

	    @Autowired
	    private FacultyProfileRepository facultyProfileRepository;

	   @Autowired
	   private UserService userService;
	   
	   @Autowired
	   private CourseRepository courseRepository;
	    
	    public List<StudentProfile> getAllStudents() {
	        return studentProfileRepository.findAll();
	    }

	    
	    public StudentProfile addStudent(StudentProfile studentRequest) {
	
	    	 User user = userService.findById(studentRequest.getUser().getId())
	                    .orElseThrow(() -> new RuntimeException("User not found"));

	    	 studentRequest.setUser(user);
	        return studentProfileRepository.save(studentRequest);
	    }

	    
	    public Optional<StudentProfile> updateStudent(Long id, StudentProfile studentProfile) {
	        if (studentProfileRepository.existsById(id)) {
	            studentProfile.setUserId(id);
	            return Optional.of(studentProfileRepository.save(studentProfile));
	        }
	        return Optional.empty();
	    }

	    
	    public void deleteStudent(Long id) {
	        studentProfileRepository.deleteById(id);
	    }

	    
	    public List<FacultyProfile> getAllFaculty() {
	        return facultyProfileRepository.findAll();
	    }

	    
	    public FacultyProfile addFaculty(FacultyProfile facultyProfile) {
	    	 User user = userService.findById(facultyProfile.getUser().getId())
	                    .orElseThrow(() -> new RuntimeException("User not found"));
	    	 facultyProfile.setUser(user);

	        return facultyProfileRepository.save(facultyProfile);
	    }

	    
	    public FacultyProfile updateFaculty(Long id, FacultyProfile facultyProfile) {
	        if (facultyProfileRepository.existsById(id)) {
	            facultyProfile.setUserId(id);
	            return facultyProfileRepository.save(facultyProfile);
	        }
	        return null; // or throw an exception
	    }

	    
	    public void deleteFaculty(Long id) {
	        facultyProfileRepository.deleteById(id);
	    }


		public boolean studentExists(Long id) {
			
			return studentProfileRepository.existsById(id);
		}
		
		public void assignCoursesToFaculty(Long facultyId, List<Long> courseIds) {
	        FacultyProfile faculty = facultyProfileRepository.findById(facultyId)
	                .orElseThrow(() -> new EntityNotFoundException("Faculty not found"));

	        Set<Course> courses = new HashSet<>();
	        for (Long courseId : courseIds) {
	            Course course = courseRepository.findById(courseId)
	                    .orElseThrow(() -> new EntityNotFoundException("Course not found"));
	            courses.add(course);
	        }

	        faculty.setCourses(courses);
	        facultyProfileRepository.save(faculty);
	    }

//	    @Override
//	    public DashboardData getDashboardData() {
//	        return dashboardRepository.getDashboardData(); // Implement this method as needed
//	    }

}
