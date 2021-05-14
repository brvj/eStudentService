package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.TransactionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;

@Service
public class DefaultTransactionService implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private DtoConverter<Transaction, TransactionDTO, DefaultTransactionDTO> transactionConverter;
	
	@Override
	public DefaultTransactionDTO getOne(Integer id) {
		Optional<Transaction> s = transactionRepo.findById(id);
		return transactionConverter.convertToDTO(s.orElse(null));
	}

	@Override
	public Integer create(DefaultTransactionDTO dto) throws IllegalArgumentException {
		Transaction t = transactionConverter.convertToJPA(dto);
		
		t = transactionRepo.save(t);
		
		return t.getId();
	}

	@Override
	public void update(Integer id, DefaultTransactionDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!transactionRepo.existsById(id)) throw new EntityNotFoundException();
		
		Transaction tNew = transactionConverter.convertToJPA(dto);
		
//		Transaction t = transactionRepo.findById(id).get();
//		Nothing to change...
//		transactionRepo.save(t);
		
	}

	@Override
	public void delete(Integer id) {
		if (!transactionRepo.existsById(id)) {}
		transactionRepo.deleteById(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FinancialCardTransactionDTO> filterTransactions(int financialCardId, Pageable pageable, FinancialCardTransactionDTO filterOptions) {
		if (filterOptions == null) {
			Page<Transaction> page = transactionRepo.findByFinancialCard_Id(financialCardId, pageable);
			return (List<FinancialCardTransactionDTO>) transactionConverter.convertToDTO(page.getContent(), FinancialCardTransactionDTO.class);
		}
		else {
			Page<Transaction> page = transactionRepo.filterTransaction(financialCardId, null, null, null, null, pageable);
			return (List<FinancialCardTransactionDTO>) transactionConverter.convertToDTO(page.getContent(), FinancialCardTransactionDTO.class);
		}
	}

}
