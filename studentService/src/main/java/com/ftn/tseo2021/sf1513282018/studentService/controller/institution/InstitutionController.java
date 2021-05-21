package com.ftn.tseo2021.sf1513282018.studentService.controller.institution;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.InstitutionExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.InstitutionTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.InstitutionUserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping(value = "api/institutions")
public class InstitutionController {
	
	@Autowired
	private InstitutionService institutionService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createInstitution(@NotNull @RequestBody DefaultInstitutionDTO institutionDTO) throws PersonalizedAccessDeniedException {
		int institutionId = institutionService.create(institutionDTO);
		return new ResponseEntity<>(institutionId, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateInstitution(@PathVariable("id") int id, @NotNull @RequestBody DefaultInstitutionDTO institutionDTO) throws PersonalizedAccessDeniedException {
		institutionService.update(id, institutionDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteInstitution(@PathVariable("id") int id) throws PersonalizedAccessDeniedException {
		institutionService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultInstitutionDTO> getInstitutionById(@PathVariable("id") int id){
		DefaultInstitutionDTO institutionDTO;
		institutionDTO = institutionService.getOne(id);
		return new ResponseEntity<>(institutionDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}/users", produces = "application/json")
	public ResponseEntity<List<InstitutionUserDTO>> getInstitutionUsers(@PathVariable("id") int id) {
		try{
			List<InstitutionUserDTO> users = institutionService.getInstitutionUsers(id, Pageable.unpaged());
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		catch(EntityNotFoundException | PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
	}
	
	@GetMapping(value = "/{id}/admins", produces = "application/json")
	public ResponseEntity<Page<InstitutionUserDTO>> getInstitutionAdmins(@PathVariable("id") int id, Pageable pageable) {
		try{
			Page<InstitutionUserDTO> admins = institutionService.getInstitutionAdmins(id, pageable);
			return new ResponseEntity<>(admins, HttpStatus.OK);
		}
		catch(EntityNotFoundException | PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND); 
		} 
	}

	@GetMapping(value = "/{id}/teachers", produces = "application/json")
	public ResponseEntity<List<InstitutionTeacherDTO>> getInstitutionTeachers(@PathVariable("id") int id){
		try{
			List<InstitutionTeacherDTO> teachers = institutionService.getInstitutionTeachers(id, Pageable.unpaged());
			return new ResponseEntity<>(teachers, HttpStatus.OK);

		}
		catch(EntityNotFoundException | PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}/students", produces = "application/json")
	public ResponseEntity<List<InstitutionStudentDTO>> getInstitutionStudents(@PathVariable("id") int id){
		try{
			List<InstitutionStudentDTO> students = institutionService.getInstitutionStudents(id, Pageable.unpaged());
			return new ResponseEntity<>(students, HttpStatus.OK);

		}
		catch(EntityNotFoundException | PersonalizedAccessDeniedException e) { 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}/courses", produces = "application/json")
	public ResponseEntity<List<InstitutionCourseDTO>> getInstitutionCourses(@PathVariable("id") int id){
		try{
			List<InstitutionCourseDTO> courses = institutionService.getInstitutionCourses(id, Pageable.unpaged());
			return new ResponseEntity<>(courses, HttpStatus.OK);

		}
		catch(EntityNotFoundException | PersonalizedAccessDeniedException e) { 
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}/examPeriods", produces = "application/json")
	public ResponseEntity<List<InstitutionExamPeriodDTO>> getInstitutionExamPeriods(@PathVariable("id") int id){
		try{
			List<InstitutionExamPeriodDTO> examPeriods = institutionService.getInstitutionExamPeriods(id, Pageable.unpaged());
			return new ResponseEntity<>(examPeriods, HttpStatus.OK);

		}
		catch(EntityNotFoundException | PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
