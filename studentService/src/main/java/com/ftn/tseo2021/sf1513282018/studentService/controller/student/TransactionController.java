package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.TransactionService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultTransactionDTO;

@RestController
@RequestMapping(value = "api/transactionTypes")
public class TransactionController {
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createTransaction(@RequestBody DefaultTransactionDTO transactionDTO) {
		int id = transactionService.create(transactionDTO);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateTransaction(@PathVariable("id") int id, 
			@RequestBody DefaultTransactionDTO transactionDTO) {
		transactionService.update(id, transactionDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTransaction(@PathVariable("id") int id) {
		transactionService.delete(id); 
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultTransactionDTO> getTransactionById(@PathVariable("id") int id) {
		DefaultTransactionDTO transaction = transactionService.getOne(id);
		
		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}
}
