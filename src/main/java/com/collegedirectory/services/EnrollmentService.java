package com.collegedirectory.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collegedirectory.entities.Enrollment;
import com.collegedirectory.respositories.EnrollmentRepository;

@Service
public class EnrollmentService {
	
	@Autowired
	private EnrollmentRepository enrollmentRepository;
	
	
	public Enrollment addEnrollment(Enrollment enrollment) {
		
		return enrollmentRepository.save(enrollment);
		
	}

}
