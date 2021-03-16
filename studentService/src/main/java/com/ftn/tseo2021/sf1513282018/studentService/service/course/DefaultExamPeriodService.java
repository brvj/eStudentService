package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamPeriodRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;

public class DefaultExamPeriodService implements ExamPeriodService {
	
	@Autowired
	ExamPeriodRepository examPeriodRepo;

	@Override
	public ExamPeriodDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(ExamPeriodDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, ExamPeriodDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
