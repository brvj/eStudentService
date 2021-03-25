package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.FinancialCardRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;

@Service
public class DefaultFinancialCardService implements FinancialCardService {
	
	@Autowired
	FinancialCardRepository financialCardRepo;

	@Override
	public DefaultFinancialCardDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultFinancialCardDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultFinancialCardDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DefaultFinancialCardDTO getByStudentId(int sutdentId) {
		// TODO Auto-generated method stub
		return null;
	}

}
