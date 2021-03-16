package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.ExamTakingRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;

public class DefaultExamTakingService implements ExamTakingService {
	
	@Autowired
	ExamTakingRepository examTakingRepo;

	@Override
	public ExamTakingDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(ExamTakingDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, ExamTakingDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
