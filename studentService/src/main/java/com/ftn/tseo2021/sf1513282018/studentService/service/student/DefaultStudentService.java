package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.StudentRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;

public class DefaultStudentService implements StudentService {
	
	@Autowired
	StudentRepository studentRepo;

	@Override
	public DefaultStudentDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultStudentDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultStudentDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DefaultStudentDTO getByUserId(int userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultStudentDTO> getByInstitutionId(int institutionId, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

}
