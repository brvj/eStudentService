package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

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

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.EnrollmentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;

@RestController
@RequestMapping(value = "api/enrollments")
public class EnrollmentController {
	
	@Autowired
	EnrollmentService enrollmentService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createEnrollment(@RequestBody DefaultEnrollmentDTO enrollmentDTO) {
		try {
			int id = enrollmentService.create(enrollmentDTO);
			
			return new ResponseEntity<>(id, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateEnrollment(@PathVariable("id") int id, 
			@RequestBody DefaultEnrollmentDTO enrollmentDTO) {
		try {
			enrollmentService.update(id, enrollmentDTO);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteEnrollment(@PathVariable("id") int id) {
		if (enrollmentService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultEnrollmentDTO> getEnrollmentById(@PathVariable("id") int id) {
		DefaultEnrollmentDTO enrollment = enrollmentService.getOne(id);
		
		if (enrollment == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(enrollment, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/examObligationTakings", produces = "application/json")
	public ResponseEntity<List<DefaultExamObligationTakingDTO>> getEnrollmentExamObligationTakings(@PathVariable("id") int id) {
		try {
			List<DefaultExamObligationTakingDTO> takings = enrollmentService.getEnrollmentExamObligationTakings(id, Pageable.unpaged());
			
			return new ResponseEntity<>(takings, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}/examTakings", produces = "application/json")
	public ResponseEntity<List<DefaultExamTakingDTO>> getEnrollmentExamTakings(@PathVariable("id") int id) {
		try {
			List<DefaultExamTakingDTO> takings = enrollmentService.getEnrollmentExamTakings(id, Pageable.unpaged());
			
			return new ResponseEntity<>(takings, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
}
