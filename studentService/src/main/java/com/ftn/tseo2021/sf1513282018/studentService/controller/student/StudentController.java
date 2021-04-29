package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CurrentPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "api/students")
public class StudentController {
	
	@Autowired
	StudentService studentService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createStudent(@RequestBody DefaultStudentDTO studentDTO) {
		try {
			int id = studentService.create(studentDTO);
			
			return new ResponseEntity<>(id, HttpStatus.CREATED);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateStudent(@PathVariable("id") int id, 
			@RequestBody DefaultStudentDTO studentDTO) {
		try {
			studentService.update(id, studentDTO);
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) {
		if (studentService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultStudentDTO> getStudentById(@PathVariable("id") int id) {
		DefaultStudentDTO student;
		try {
			student = studentService.getOne(id);
			if (student == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(student, HttpStatus.OK);
		} catch (ForbiddenAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/user/{id}", produces = "application/json")
	public ResponseEntity<DefaultStudentDTO> getStudentByUserId(@PathVariable("id") int id){
		try{
			DefaultStudentDTO studentDTO = studentService.getByUserId(id);
			return new ResponseEntity<>(studentDTO, HttpStatus.OK);

		}catch(EntityNotFoundException e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}
	
	@GetMapping(value = "/{id}/enrollments", produces = "application/json")
	public ResponseEntity<List<StudentEnrollmentDTO>> getStudentEnrollments(@PathVariable("id") int id) {
		try {
			List<StudentEnrollmentDTO> enrollments = studentService.getStudentEnrollments(id, Pageable.unpaged());
			
			return new ResponseEntity<>(enrollments, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}/documents", produces = "application/json")
	public ResponseEntity<List<StudentDocumentDTO>> getStudentDocuments(@PathVariable("id") int id) {
		try {
			List<StudentDocumentDTO> documents = studentService.getStudentDocuments(id, Pageable.unpaged());
			
			return new ResponseEntity<>(documents, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(value = "/{id}/financialCard", produces = "application/json")
	public ResponseEntity<DefaultFinancialCardDTO> getStudentFinancialCard(@PathVariable("id") int id) {
		try {
			DefaultFinancialCardDTO fcard = studentService.getStudentFinancialCard(id);
			
			return new ResponseEntity<>(fcard, HttpStatus.OK);
		} catch (EntityNotFoundException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ModelAndView getInstitutionStudents(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/students", principal.getInstitutionId()));
	}
	
}
