package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.CourseExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamOblExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamObligationService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "api/examObligations")
public class ExamObligationController {
	
	@Autowired
	ExamObligationService examObligationService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createExamObligation(@RequestBody DefaultExamObligationDTO examObligationDTO){
		int id = examObligationService.create(examObligationDTO);

		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateExamObligation(@PathVariable("id") int id, @RequestBody DefaultExamObligationDTO examObligationDTO){
		examObligationService.update(id, examObligationDTO);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteExamObligation(@PathVariable("id") int id){
		examObligationService.delete(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultExamObligationDTO> getExamObligationById(@PathVariable("id") int id){
		DefaultExamObligationDTO examObligationDTO;

		examObligationDTO = examObligationService.getOne(id);
			
		if(examObligationDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(examObligationDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/examObligationTakings", produces = "application/json")
	public ResponseEntity<Page<ExamOblExamObligationTakingDTO>> getExamObligationTakings(@PathVariable("id") int id){
		Page<ExamOblExamObligationTakingDTO> examObligationTakingList = examObligationService.getExamObligationTakings(id, Pageable.unpaged());

		if(examObligationTakingList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(examObligationTakingList, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/course",produces = "application/json")
	public ResponseEntity<Page<CourseExamObligationDTO>> getCourseExamObligations(@PathVariable("id") int id, Pageable pageable){
		Page<CourseExamObligationDTO> page = examObligationService.filterExamObligations(id, pageable, null);

		if(page == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(page, HttpStatus.OK);
	}
}
