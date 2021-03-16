package com.ftn.tseo2021.sf1513282018.studentService.service.teacher;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher.TeacherTitleRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherTitleService;

public class DefaultTeacherTitleService implements TeacherTitleService {
	
	@Autowired
	TeacherTitleRepository teacherTitleRepo;

	@Override
	public TeacherTitleDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(TeacherTitleDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, TeacherTitleDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
