package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.CurrentPrincipal;

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
		int id = studentService.create(studentDTO);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateStudent(@PathVariable("id") int id, 
			@RequestBody DefaultStudentDTO studentDTO) {
		studentService.update(id, studentDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteStudent(@PathVariable("id") int id) {
		studentService.delete(id); 
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultStudentDTO> getStudentById(@PathVariable("id") int id) {
		DefaultStudentDTO student = studentService.getOne(id);
		
		return new ResponseEntity<>(student, HttpStatus.OK);
	}
	
	@GetMapping(value = "/user/{id}", produces = "application/json")
	public ResponseEntity<DefaultStudentDTO> getStudentByUserId(@PathVariable("id") int id){
		DefaultStudentDTO studentDTO = studentService.getByUserId(id);
		return new ResponseEntity<>(studentDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/enrollments", produces = "application/json")
	public ResponseEntity<Page<StudentEnrollmentDTO>> getStudentEnrollments(@PathVariable("id") int id, Pageable pageable) {
		Page<StudentEnrollmentDTO> enrollments = studentService.getStudentEnrollments(id, pageable);
		return new ResponseEntity<>(enrollments, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/documents", produces = "application/json")
	public ResponseEntity<Page<StudentDocumentDTO>> getStudentDocuments(@PathVariable("id") int id, Pageable pageable) {
		Page<StudentDocumentDTO> documents = studentService.getStudentDocuments(id, pageable);
		return new ResponseEntity<>(documents, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/financialCard", produces = "application/json")
	public ResponseEntity<DefaultFinancialCardDTO> getStudentFinancialCard(@PathVariable("id") int id) {
		DefaultFinancialCardDTO fcard = studentService.getStudentFinancialCard(id);
		return new ResponseEntity<>(fcard, HttpStatus.OK);
	}
	
	@GetMapping
	public ModelAndView getInstitutionStudents(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/students", principal.getInstitutionId()));
	}
}
