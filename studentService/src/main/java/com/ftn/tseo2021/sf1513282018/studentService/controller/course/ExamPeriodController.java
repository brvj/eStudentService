package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.ExamPeriodExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.CurrentPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.ExamPeriodService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "api/examPeriods")
public class ExamPeriodController {
	
	@Autowired
	ExamPeriodService examPeriodService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createExamPeriod(@RequestBody DefaultExamPeriodDTO examPeriodDTO){
		try{
			int id = examPeriodService.create(examPeriodDTO);

			return new ResponseEntity<>(id, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateExamPeriod(@PathVariable("id") int id, @RequestBody DefaultExamPeriodDTO examPeriodDTO){
		try{
			examPeriodService.update(id, examPeriodDTO);

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
	public ResponseEntity<Void> deleteExamPeriod(@PathVariable("id") int id){
		try{
			examPeriodService.delete(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultExamPeriodDTO> getExamPeriodById(@PathVariable("id") int id){
		DefaultExamPeriodDTO examPeriodDTO;
		try {
			examPeriodDTO = examPeriodService.getOne(id);
			if(examPeriodDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(examPeriodDTO, HttpStatus.OK);
		} catch (PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ModelAndView getInstitutionExamPeriods(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/examPeriods", principal.getInstitutionId()));
	}

	@GetMapping(value = "/{id}/examPeriodExams", produces = "application/json")
	public ResponseEntity<List<ExamPeriodExamDTO>> getExamPeriodExams(@PathVariable("id") int id){
		List<ExamPeriodExamDTO> examPeriodExamDTOList = examPeriodService.getExamPeriodExams(id, Pageable.unpaged());

		if(examPeriodExamDTOList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(examPeriodExamDTOList, HttpStatus.OK);
	}
}
