package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationTypeService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "api/examObligationTypes")
public class ExamObligationTypeController {

	@Autowired
	ExamObligationTypeService examObligationTypeService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createExamObligationType(@RequestBody DefaultExamObligationTypeDTO examObligationTypeDTO){
		try{
			int id = examObligationTypeService.create(examObligationTypeDTO);

			return new ResponseEntity<>(id, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateExamObligationType(@PathVariable("id") int id, @RequestBody DefaultExamObligationTypeDTO examObligationTypeDTO){
		try{
			examObligationTypeService.update(id, examObligationTypeDTO);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (EntityNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExamObligationType(@PathVariable("id") int id){
		if(examObligationTypeService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultExamObligationTypeDTO> getExamObligationTypeById(@PathVariable("id") int id){
		DefaultExamObligationTypeDTO examObligationTypeDTO;
		try {
			examObligationTypeDTO = examObligationTypeService.getOne(id);
			
			if(examObligationTypeDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(examObligationTypeDTO, HttpStatus.OK);
		} catch (ForbiddenAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
