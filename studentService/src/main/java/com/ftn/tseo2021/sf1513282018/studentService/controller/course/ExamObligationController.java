package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "api/examObligations")
public class ExamObligationController {
	
	@Autowired
	ExamObligationService examObligationService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createExamObligation(@RequestBody DefaultExamObligationDTO examObligationDTO){
		try{
			int id = examObligationService.create(examObligationDTO);

			return new ResponseEntity<>(id, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateExamObligation(@PathVariable("id") int id, @RequestBody DefaultExamObligationDTO examObligationDTO){
		try{
			examObligationService.update(id, examObligationDTO);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (EntityNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExamObligation(@PathVariable("id") int id){
		if(examObligationService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultExamObligationDTO> getExamObligationById(@PathVariable("id") int id){
		DefaultExamObligationDTO examObligationDTO = examObligationService.getOne(id);

		if(examObligationDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
