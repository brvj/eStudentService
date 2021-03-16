package com.ftn.tseo2021.sf1513282018.studentService.service.course;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course.ExamObligationTypeRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationTypeService;

public class DefaultExamObligationTypeService implements ExamObligationTypeService {
	
	@Autowired
	ExamObligationTypeRepository examObligationTypeRepo;

	@Override
	public ExamObligationTypeDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(ExamObligationTypeDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, ExamObligationTypeDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
