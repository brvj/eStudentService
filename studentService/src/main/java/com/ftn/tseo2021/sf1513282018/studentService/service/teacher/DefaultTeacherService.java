package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;

public class DefaultTeacherService implements TeacherService {
	
	@Autowired
	TeacherRepository teacherRepo;

	@Override
	public DefaultTeacherDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultTeacherDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultTeacherDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
