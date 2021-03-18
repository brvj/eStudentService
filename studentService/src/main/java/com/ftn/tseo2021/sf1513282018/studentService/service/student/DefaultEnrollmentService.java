package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.EnrollmentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;

public class DefaultEnrollmentService implements EnrollmentService {
	
	@Autowired
	EnrollmentRepository enrollmentRepo;

	@Override
	public DefaultEnrollmentDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultEnrollmentDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultEnrollmentDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
