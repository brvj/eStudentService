package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;

public class DefaultExamService implements ExamService {
	
	@Autowired
	ExamRepository examRepo;

	@Override
	public DefaultExamDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultExamDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultExamDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
