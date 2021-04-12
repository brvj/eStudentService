package com.ftn.tseo2021.sf1513282018.studentService.converter.student;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.FinancialCardRepository;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;

@Component
public class TransactionConverter implements DtoConverter<Transaction, TransactionDTO, DefaultTransactionDTO> {
	
	@Autowired
	DtoConverter<FinancialCard, FinancialCardDTO, DefaultFinancialCardDTO> financialCardConverter;
	
	@Autowired
	FinancialCardRepository financialCardRepo;
	
	
	@Override
	public Transaction convertToJPA(TransactionDTO source) throws IllegalArgumentException {
		if (source instanceof DefaultTransactionDTO) return convertToJPA((DefaultTransactionDTO) source);
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", source.getClass().toString()));
	}

	@Override
	public List<Transaction> convertToJPA(List<? extends TransactionDTO> sources) throws IllegalArgumentException {
		List<Transaction> result = new ArrayList<Transaction>();
		
		if (sources.get(0) instanceof DefaultTransactionDTO) {
			for (TransactionDTO dto : sources) result.add(convertToJPA((DefaultTransactionDTO) dto));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting from %s type is not supported", sources.get(0).getClass().toString()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends TransactionDTO> T convertToDTO(Transaction source, Class<? extends TransactionDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultTransactionDTO.class) return (T) convertToDefaultTransactionDTO(source);
		else if (returnType == FinancialCardTransactionDTO.class) return (T) convertToFinancialCardTransactionDTO(source);
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public List<? extends TransactionDTO> convertToDTO(List<Transaction> sources,
			Class<? extends TransactionDTO> returnType) throws IllegalArgumentException {
		if (returnType == DefaultTransactionDTO.class) {
			List<DefaultTransactionDTO> result = new ArrayList<>();
			for (Transaction jpa : sources) result.add(convertToDefaultTransactionDTO(jpa));
			return result;
		}
		else if (returnType == FinancialCardTransactionDTO.class) {
			List<FinancialCardTransactionDTO> result = new ArrayList<>();
			for (Transaction jpa : sources) result.add(convertToFinancialCardTransactionDTO(jpa));
			return result;
		}
		else throw new IllegalArgumentException(String.format(
				"Converting to %s type is not supported", returnType.toString()));
	}

	@Override
	public DefaultTransactionDTO convertToDTO(Transaction source) {
		return convertToDefaultTransactionDTO(source);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DefaultTransactionDTO> convertToDTO(List<Transaction> sources) {
		return (List<DefaultTransactionDTO>) convertToDTO(sources, DefaultTransactionDTO.class);
	}
	
	private DefaultTransactionDTO convertToDefaultTransactionDTO(Transaction source) {
		if (source == null) return null;
		
		DefaultTransactionDTO dto = new DefaultTransactionDTO(source.getId(), source.getAmmount(), 
				source.getDate(), source.getDescription(), source.getTransactionType(), 
				financialCardConverter.convertToDTO(source.getFinancialCard()));
		
		return dto;
	}
	
	private FinancialCardTransactionDTO convertToFinancialCardTransactionDTO(Transaction source) {
		if (source == null) return null;
		
		FinancialCardTransactionDTO dto = new FinancialCardTransactionDTO(source.getId(), source.getAmmount(), 
				source.getDate(), source.getDescription(), source.getTransactionType());
		
		return dto;
	}

	private Transaction convertToJPA(DefaultTransactionDTO source) throws IllegalArgumentException {
		if (source == null) return null;
		
		if (source.getFinancialCard() == null || 
				!financialCardRepo.existsById(source.getFinancialCard().getId()))
			throw new IllegalArgumentException();
		
		Transaction transaction = new Transaction(source.getId(), source.getAmmount(), source.getDate(), 
				source.getDescription(), source.getType(), 
				financialCardRepo.findById(source.getFinancialCard().getId()).get());
		
		return transaction;
	}
}
