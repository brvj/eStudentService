package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.TransactionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;

public class DefaultTransactionService implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepo;

	@Override
	public DefaultTransactionDTO getOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer create(DefaultTransactionDTO t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Integer id, DefaultTransactionDTO t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean delete(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
