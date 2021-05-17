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

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.ExamTakingService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;

@RestController
@RequestMapping(value = "api/examTakings")
public class ExamTakingController {
	
	@Autowired
	ExamTakingService examTakingService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createExamTaking(@RequestBody DefaultExamTakingDTO takingDTO) {
		int id = examTakingService.create(takingDTO);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateExamTaking(@PathVariable("id") int id, 
			@RequestBody DefaultExamTakingDTO takingDTO) {
		examTakingService.update(id, takingDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExamTaking(@PathVariable("id") int id) {
		examTakingService.delete(id); 
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping(value = "/{id", produces = "application/json")
	public ResponseEntity<DefaultExamTakingDTO> getExamTakingById(@PathVariable("id") int id) {
		DefaultExamTakingDTO taking = examTakingService.getOne(id);
		return new ResponseEntity<>(taking, HttpStatus.OK);
	}

}
