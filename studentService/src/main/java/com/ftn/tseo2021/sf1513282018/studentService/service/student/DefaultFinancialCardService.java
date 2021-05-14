package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;

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
	public DefaultFinancialCardDTO getOne(Integer id) {
		Optional<FinancialCard> fc = financialCardRepo.findById(id);
		return financialCardConverter.convertToDTO(fc.orElse(null));
	}

	@Override
	public Integer create(DefaultFinancialCardDTO dto) throws IllegalArgumentException {
		FinancialCard fc = financialCardConverter.convertToJPA(dto);
		
//		fc = financialCardRepo.save(fc);
		
		return fc.getId();
	}

	@Override
	public void update(Integer id, DefaultFinancialCardDTO dto) throws EntityNotFoundException, IllegalArgumentException {
		if (!financialCardRepo.existsById(id)) throw new EntityNotFoundException();
		
		FinancialCard fNew = financialCardConverter.convertToJPA(dto);
		
//		FinancialCard f = financialCardRepo.findById(id).get();
//		Nothing to change...
//		financialCardRepo.save(f);
		
	}

	@Override
	public void delete(Integer id) {
		if (!financialCardRepo.existsById(id)) {}
		financialCardRepo.deleteById(id);
	}

	@Override
	public DefaultFinancialCardDTO getByStudentId(int studentId) {
		Optional<FinancialCard> fc = financialCardRepo.findByStudent_Id(studentId);

		return financialCardConverter.convertToDTO(fc.orElse(null));
	}

	@Override
	public List<FinancialCardTransactionDTO> getFinancialCardTransactions(int cardId, Pageable pageable) throws EntityNotFoundException {
		if(!financialCardRepo.existsById(cardId)) throw new EntityNotFoundException();

		List<FinancialCardTransactionDTO> transactions = transactionService.filterTransactions(cardId, pageable, null);

		return transactions;
	}
}
