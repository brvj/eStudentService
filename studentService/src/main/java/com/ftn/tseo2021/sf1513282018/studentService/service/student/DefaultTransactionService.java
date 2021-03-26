package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution.InstitutionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.FinancialCardRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.TransactionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;

@Service
public class DefaultTransactionService implements TransactionService {
	
	@Autowired
	TransactionRepository transactionRepo;
	
	@Autowired
	private FinancialCardRepository financialCardRepo;

	@Autowired
	DtoConverter<Transaction, TransactionDTO, DefaultTransactionDTO> transactionConverter;
	
	
	@Override
	public DefaultTransactionDTO getOne(Integer id) {
		Optional<Transaction> s = transactionRepo.findById(id);
		return transactionConverter.convertToDTO(s.orElse(null));
	}

	@Override
	public Integer create(DefaultTransactionDTO dto) {
		Transaction t = transactionConverter.convertToJPA(dto);
		
		t = transactionRepo.save(t);
		
		return t.getId();
	}

	@Override
	public void update(Integer id, DefaultTransactionDTO dto) {
		if (!transactionRepo.existsById(id)) throw new EntityNotFoundException();
		
		dto.setId(id);
		Transaction t = transactionConverter.convertToJPA(dto);
		
		transactionRepo.save(t);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!transactionRepo.existsById(id)) return false;
		transactionRepo.deleteById(id);
		return true;
	}

	@Override
	public List<DefaultTransactionDTO> getByFinancialCardId(int financialCardId, Pageable pageable) {
		if(!financialCardRepo.existsById(financialCardId)) throw new EntityNotFoundException();

		Page<Transaction> page = transactionRepo.filterTransaction(financialCardId, null, null, null, null, pageable);

		return transactionConverter.convertToDTO(page.getContent());
	}

}
