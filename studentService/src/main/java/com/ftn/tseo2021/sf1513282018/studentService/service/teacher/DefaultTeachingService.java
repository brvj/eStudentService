package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;

public class DefaultTeachingService implements TeachingService {
	
	@Autowired
	TeachingRepository teachingRepo;

	@Override
	public TeachingDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(TeachingDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, TeachingDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
