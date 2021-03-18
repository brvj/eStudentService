package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.List;

import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;

@Component
public class TransactionConverter implements DtoConverter<Transaction, TransactionDTO, DefaultTransactionDTO> {

	@Override
	public Transaction convertToJPA(TransactionDTO source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Transaction> convertToJPA(List<? extends TransactionDTO> sources) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends TransactionDTO> T convertToDTO(Transaction source, Class<? extends TransactionDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<? extends TransactionDTO> convertToDTO(List<Transaction> sources,
			Class<? extends TransactionDTO> returnType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DefaultTransactionDTO convertToDTO(Transaction source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<DefaultTransactionDTO> convertToDTO(List<Transaction> sources) {
		// TODO Auto-generated method stub
		return null;
	}

}
