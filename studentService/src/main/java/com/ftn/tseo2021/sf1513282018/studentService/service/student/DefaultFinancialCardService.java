package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.Optional;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.FinancialCardRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.TransactionType;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeStudentOrAdmin;


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
	
	@Autowired
	private PersonalizedAuthorizator authorizator;

	@AuthorizeStudentOrAdmin
	@Override
	public DefaultFinancialCardDTO getOne(Integer id) {
		Optional<FinancialCard> fc = financialCardRepo.findById(id);
		return financialCardConverter.convertToDTO(fc.orElse(null));
	}

	@Override
	public Integer create(DefaultFinancialCardDTO dto) {
		FinancialCard fc = financialCardConverter.convertToJPA(dto);
		
//		fc = financialCardRepo.save(fc);
		
		return fc.getId();
	}

	@Override
	public void update(Integer id, DefaultFinancialCardDTO dto) {
		if (!financialCardRepo.existsById(id)) throw new ResourceNotFoundException();
		
		FinancialCard fNew = financialCardConverter.convertToJPA(dto);
		
//		FinancialCard f = financialCardRepo.findById(id).get();
//		Nothing to change...
//		financialCardRepo.save(f);
		
	}
	
	@Override
	public void update(Transaction transaction) {
		FinancialCard fCard = transaction.getFinancialCard();
		
		if (transaction.getTransactionType().equals(TransactionType.PAYMENT)) {
			fCard.setCurrentAmmout(fCard.getCurrentAmmout() - transaction.getAmmount());
			fCard.setTotalSpent(fCard.getTotalSpent() + transaction.getAmmount());
		}
		else if (transaction.getTransactionType().equals(TransactionType.DEPOSIT)) {
			fCard.setCurrentAmmout(fCard.getCurrentAmmout() + transaction.getAmmount());
			fCard.setTotalDeposit(fCard.getTotalDeposit() + transaction.getAmmount());
		}
		
		financialCardRepo.save(fCard);
	}

	@Override
	public void delete(Integer id) {
		if (!financialCardRepo.existsById(id)) {}
		financialCardRepo.deleteById(id);
	}

	@AuthorizeStudentOrAdmin
	@Override
	public DefaultFinancialCardDTO getByStudentId(int studentId) {
		Optional<FinancialCard> fc = financialCardRepo.findByStudent_Id(studentId);

		return financialCardConverter.convertToDTO(fc.orElseThrow(() -> new ResourceNotFoundException()));
	}

	@Override
	public Page<FinancialCardTransactionDTO> getFinancialCardTransactions(int cardId, Pageable pageable) {
		if(!financialCardRepo.existsById(cardId)) throw new ResourceNotFoundException();

		Page<FinancialCardTransactionDTO> transactions = transactionService.filterTransactions(cardId, pageable, null);

		return transactions;
	}
}
