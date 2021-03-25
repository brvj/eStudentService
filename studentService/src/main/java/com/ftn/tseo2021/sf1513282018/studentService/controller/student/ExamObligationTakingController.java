package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import javax.persistence.EntityNotFoundException;

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

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamObligationTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;

@RestController
@RequestMapping(value = "api/examObligationTakings")
public class ExamObligationTakingController {
	
	@Autowired
	ExamObligationTakingService examObligationTakingService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createExamObligationTaking(@RequestBody DefaultExamObligationTakingDTO takingDTO) {
		try {
			int id = examObligationTakingService.create(takingDTO);
			
			return new ResponseEntity<>(id, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateExamObligationTaking(@PathVariable("id") int id, 
			@RequestBody DefaultExamObligationTakingDTO takingDTO) {
		try {
			examObligationTakingService.update(id, takingDTO);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExamObligationTaking(@PathVariable("id") int id) {
		if (examObligationTakingService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultExamObligationTakingDTO> getExamObligationTakingById(@PathVariable("id") int id) {
		DefaultExamObligationTakingDTO taking = examObligationTakingService.getOne(id);
		
		if (taking == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(taking, HttpStatus.OK);
	}

}
