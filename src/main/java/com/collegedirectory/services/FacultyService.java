package com.collegedirectory.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegedirectory.entities.FacultyProfile;
import com.collegedirectory.entities.StudentProfile;
import com.collegedirectory.entities.User;
import com.collegedirectory.respositories.FacultyProfileRepository;

@Service
public class FacultyService {

    @Autowired
    private FacultyProfileRepository facultyProfileRepository;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private StudentService studentService;

    
    public Optional<FacultyProfile> findByUser(User user) {
        return facultyProfileRepository.findByUser(user);
    }

    
    public List<FacultyProfile> getAllFaculty() {
        return facultyProfileRepository.findAll();
    }

    
    public FacultyProfile addFaculty(FacultyProfile facultyProfile) {
        return facultyProfileRepository.save(facultyProfile);
    }
    
    public List<FacultyProfile> getFacultyAdvisors(Long departmentId) {
        return facultyProfileRepository.findByDepartmentId(departmentId);
    }

    
    
    public Optional<FacultyProfile> updateFaculty(Long id, FacultyProfile facultyProfile) {
        if (facultyProfileRepository.existsById(id)) {
            facultyProfile.setUserId(id); // Set the ID for the update
            return Optional.of(facultyProfileRepository.save(facultyProfile)); // Return the updated faculty profile
        }
        return Optional.empty(); // Return empty if not found
    }
    
//    public Optional<List<StudentProfile>> findClassListByUser(String username) {
//    	
//        return studentService.findClassListByDepartmentId(userService.findByUsername(username).get().);
//    }
    
    public Optional<FacultyProfile> findById(Long id){
    	
    	return facultyProfileRepository.findById(id);
    }
    public void deleteFaculty(Long id) {
        facultyProfileRepository.deleteById(id);
    }


	public Object getFacultyCourseLoad(String orElse) {
		// TODO Auto-generated method stub
		return null;
	}


	
}
