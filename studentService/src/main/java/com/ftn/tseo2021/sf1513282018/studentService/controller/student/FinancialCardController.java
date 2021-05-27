package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.FinancialCardTransactionDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
		int id = cardService.create(financialCardDTO);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateFinancialCard(@PathVariable("id") int id, 
			@RequestBody DefaultFinancialCardDTO financialCardDTO) {
		cardService.update(id, financialCardDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteFinancialCard(@PathVariable("id") int id) {
		cardService.delete(id); 
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultFinancialCardDTO> getFinancialCardById(@PathVariable("id") int id) {
		DefaultFinancialCardDTO card = cardService.getOne(id);
		return new ResponseEntity<>(card, HttpStatus.OK);
		}
	
	@GetMapping(value = "/student/{id}", produces = "application/json")
	public ResponseEntity<DefaultFinancialCardDTO> getFinancialCardByStudentId(@PathVariable("id") int id){
		DefaultFinancialCardDTO financialCardDTO = cardService.getByStudentId(id);
		return new ResponseEntity<>(financialCardDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/transactions", produces = "application/json")
	public ResponseEntity<Page<FinancialCardTransactionDTO>> getFinancialCardTransactions(@PathVariable("id") int id, Pageable pageable){
		Page<FinancialCardTransactionDTO> transactions = cardService.getFinancialCardTransactions(id, pageable);
		return new ResponseEntity<>(transactions, HttpStatus.OK);
	}
}
