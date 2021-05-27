package com.ftn.tseo2021.sf1513282018.studentService.service.student;

import java.util.function.Function;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ftn.tseo2021.sf1513282018.studentService.contract.converter.DtoConverter;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student.TransactionRepository;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.converter.student.FinancialCardConverter;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.EntityValidationException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ResourceNotFoundException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.FinancialCard;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.PersonalizedAuthorizator;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeAdmin;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.AuthorizeStudentOrAdmin;

@Service
public class DefaultTransactionService implements TransactionService {
	
	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private DtoConverter<Transaction, TransactionDTO, DefaultTransactionDTO> transactionConverter;
	
	@Autowired
	private FinancialCardService financialCardService;
	
	@Autowired
	private FinancialCardConverter cardConverter;
	
	@Autowired
	private StudentService studentService;
	
	
	@Autowired
	private PersonalizedAuthorizator authorizator;
	
	private CustomPrincipal getPrincipal() { return authorizator.getPrincipal(); }
	
	@AuthorizeStudentOrAdmin
	@Override
	public DefaultTransactionDTO getOne(Integer id) {
		if (!getPrincipal().isAdmin() && 
				(getPrincipal().isAdmin() || getPrincipal().isStudent()))
			authorizator.assertPrincipalIdIs(id, PersonalizedAccessDeniedException.class);
		
		Transaction transaction = transactionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		if (getPrincipal().isAdmin())
			authorizator.assertPrincipalIsFromInstitution(transaction.getFinancialCard().getStudent().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		return transactionConverter.convertToDTO(transaction);
	}

	@AuthorizeAdmin
	@Override
	public Integer create(DefaultTransactionDTO dto) {
		try {
			DefaultFinancialCardDTO fCardDTO = financialCardService.getOne(dto.getFinancialCard().getId());

			FinancialCard card = cardConverter.convertToJPA(fCardDTO);		
			if (getPrincipal().isAdmin())
				authorizator.assertPrincipalIsFromInstitution(card.getStudent().getInstitution().getId(), EntityValidationException.class);
		} 
		catch (ResourceNotFoundException | NullPointerException e) {throw new EntityValidationException();}
		Transaction transaction = transactionConverter.convertToJPA(dto);
		
		transaction = transactionRepo.save(transaction);

		return transaction.getId();
	}
	
	@AuthorizeAdmin
	@Override
	public void update(Integer id, DefaultTransactionDTO dto) {
		Transaction t = transactionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());
		
		authorizator.assertPrincipalIsFromInstitution(t.getFinancialCard().getStudent().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		if (dto.getAmmount() < 0 || dto.getDate() == null || dto.getDescription() == null || dto.getFinancialCard().getId() != t.getFinancialCard().getId())
			throw new EntityValidationException();

		Transaction tNew = transactionConverter.convertToJPA(dto);
		
		t.setDate(tNew.getDate());
		transactionRepo.save(t);
		
	}

	@AuthorizeAdmin
	@Override
	public void delete(Integer id) {
		Transaction t = transactionRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException());

		authorizator.assertPrincipalIsFromInstitution(t.getFinancialCard().getStudent().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		
		transactionRepo.deleteById(id);
	}

	@AuthorizeStudentOrAdmin
	@SuppressWarnings("unchecked")
	@Override
	public Page<FinancialCardTransactionDTO> filterTransactions(int financialCardId, Pageable pageable, FinancialCardTransactionDTO filterOptions) {
		DefaultFinancialCardDTO fCard = financialCardService.getOne(financialCardId);
		
		FinancialCard financialCard = cardConverter.convertToJPA(fCard);
		if (getPrincipal().isStudent())
			authorizator.assertStudentIdIs(financialCard.getStudent().getId(), PersonalizedAccessDeniedException.class);
		else if (getPrincipal().isAdmin()) {
			authorizator.assertPrincipalIsFromInstitution(financialCard.getStudent().getInstitution().getId(), PersonalizedAccessDeniedException.class);
		}

		Page<Transaction> page;

		if (filterOptions == null) {
			page = transactionRepo.findByFinancialCard_Id(financialCardId, pageable);
		}
		else {
			page = transactionRepo.filterTransaction(financialCardId, null, null, null, null, pageable);
		}
		return page.map(new Function<Transaction, FinancialCardTransactionDTO>() {
			@Override
			public FinancialCardTransactionDTO apply(Transaction transaction) {
				return transactionConverter.convertToDTO(transaction, FinancialCardTransactionDTO.class);
			}
		});
	}
}
