package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeachingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;

public class DefaultTeachingService implements TeachingService {
	
	@Autowired
	TeachingRepository teachingRepo;

	@Override
	public DefaultTeachingDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultTeachingDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultTeachingDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
