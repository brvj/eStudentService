package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/financialCards")
public class FinancialCardController {
	
	@Autowired
	FinancialCardService cardService;
	
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createFinancialCard(@RequestBody DefaultFinancialCardDTO financialCardDTO) {
		try {
			int id = cardService.create(financialCardDTO);
			
			return new ResponseEntity<>(id, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateFinancialCard(@PathVariable("id") int id, 
			@RequestBody DefaultFinancialCardDTO financialCardDTO) {
		try {
			cardService.update(id, financialCardDTO);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteFinancialCard(@PathVariable("id") int id) {
		if (cardService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultFinancialCardDTO> getFinancialCardById(@PathVariable("id") int id) {
		DefaultFinancialCardDTO financialCard;
		try {
			financialCard = cardService.getOne(id);
			
			if (financialCard == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(financialCard, HttpStatus.OK);
		} catch (ForbiddenAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/student/{id}", produces = "application/json")
	public ResponseEntity<DefaultFinancialCardDTO> getFinancialCardByStudentId(@PathVariable("id") int id){
		try{
			DefaultFinancialCardDTO financialCardDTO = cardService.getByStudentId(id);
			return new ResponseEntity<>(financialCardDTO, HttpStatus.OK);

		}catch(EntityNotFoundException e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}
	
	@GetMapping(value = "/{id}/transactions", produces = "application/json")
	public ResponseEntity<List<FinancialCardTransactionDTO>> getFinancialCardTransactions(@PathVariable("id") int id){
		try{
			List<FinancialCardTransactionDTO> transactions = cardService.getFinancialCardTransactions(id, Pageable.unpaged());
			return new ResponseEntity<>(transactions, HttpStatus.OK);

		}catch(EntityNotFoundException e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}
}
