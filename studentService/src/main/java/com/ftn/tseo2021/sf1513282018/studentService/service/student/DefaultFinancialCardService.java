package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.FinancialCardRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;


@Service
public class DefaultFinancialCardService implements FinancialCardService {
	
	@Autowired
	FinancialCardRepository financialCardRepo;
	
	@Autowired
	StudentService studentService;

	@Autowired
	TransactionService transactionService;
	
	@Autowired
	DtoConverter<FinancialCard, FinancialCardDTO, DefaultFinancialCardDTO> financialCardConverter;
	
	@Override
	public boolean existsById(Integer id) {
		return financialCardRepo.existsById(id);
	}

	@Override
	public DefaultFinancialCardDTO getOne(Integer id) {
		Optional<FinancialCard> fc = financialCardRepo.findById(id);
		return financialCardConverter.convertToDTO(fc.orElse(null));
	}

	@Override
	public Integer create(DefaultFinancialCardDTO t) {
		FinancialCard fc = financialCardConverter.convertToJPA(t);
		
		fc = financialCardRepo.save(fc);
		
		return fc.getId();
	}

	@Override
	public void update(Integer id, DefaultFinancialCardDTO t) {
		if (!financialCardRepo.existsById(id)) throw new EntityNotFoundException();
		
		t.setId(id);
		FinancialCard fc = financialCardConverter.convertToJPA(t);
		
		financialCardRepo.save(fc);
		
	}

	@Override
	public boolean delete(Integer id) {
		if (!financialCardRepo.existsById(id)) return false;
		financialCardRepo.deleteById(id);
		return true;
	}

	@Override
	public DefaultFinancialCardDTO getByStudentId(int studentId) {
		if(studentService.getOne(studentId) == null) throw new EntityNotFoundException();

		Optional<FinancialCard> fc = financialCardRepo.findByStudent_Id(studentId);

		return financialCardConverter.convertToDTO(fc.orElse(null));
	}

	@Override
	public List<DefaultTransactionDTO> getFinancialCardTransactions(int cardId, Pageable pageable) throws EntityNotFoundException {
		if(!financialCardRepo.existsById(cardId)) throw new EntityNotFoundException();

		List<DefaultTransactionDTO> transactions = transactionService.getByFinancialCardId(cardId, pageable);

		return transactions;
	}
}
