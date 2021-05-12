package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "api/exams")
public class ExamController {
	
	@Autowired
	ExamService examService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createExam(@RequestBody DefaultExamDTO examDTO){
		try{
			int id = examService.create(examDTO);

			return new ResponseEntity<>(id, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateExam(@PathVariable("id") int id, @RequestBody DefaultExamDTO examDTO){
		try{
			examService.update(id, examDTO);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (EntityNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExam(@PathVariable("id") int id){
		try{
			examService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultExamDTO> getExamById(@PathVariable("id") int id){
		DefaultExamDTO examDTO;
		try {
			examDTO = examService.getOne(id);
			
			if(examDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(examDTO, HttpStatus.OK);
		} catch (PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
