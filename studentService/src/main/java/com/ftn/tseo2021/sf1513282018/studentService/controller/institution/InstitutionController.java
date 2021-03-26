package com.ftn.tseo2021.sf1513282018.studentService.controller.institution;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "api/institutions")
public class InstitutionController {
	
	@Autowired
	InstitutionService institutionService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createInstitution(@NotNull @RequestBody DefaultInstitutionDTO institutionDTO){
		try{
			int institutionId = institutionService.create(institutionDTO);
			return new ResponseEntity<>(institutionId, HttpStatus.CREATED);

		}catch(IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateInstitution(@PathVariable("id") int id, @NotNull @RequestBody DefaultInstitutionDTO institutionDTO){
		try{
			institutionService.update(id, institutionDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}catch(EntityNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException ex){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteInstitution(@PathVariable("id") int id){
		if(institutionService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultInstitutionDTO> getInstitutionById(@PathVariable("id") int id){
		DefaultInstitutionDTO institutionDTO = institutionService.getOne(id);

		if (institutionDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(institutionDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/users", produces = "application/json")
	public ResponseEntity<List<DefaultUserDTO>> getInstitutionUsers(@PathVariable("id") int id){
		try{
			List<DefaultUserDTO> users = institutionService.getInstitutionUsers(id, Pageable.unpaged());
			return new ResponseEntity<>(users, HttpStatus.OK);

		}catch(EntityNotFoundException e){ return new ResponseEntity<>(HttpStatus.NOT_FOUND); }
	}

	@GetMapping(value = "/{id}/teachers", produces = "application/json")
	public ResponseEntity<List<DefaultTeacherDTO>> getInstitutionTeachers(@PathVariable("id") int id){
		try{
			List<DefaultTeacherDTO> teachers = institutionService.getInstitutionTeachers(id, Pageable.unpaged());
			return new ResponseEntity<>(teachers, HttpStatus.OK);

		}catch(EntityNotFoundException e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}

	@GetMapping(value = "/{id}/students", produces = "application/json")
	public ResponseEntity<List<DefaultStudentDTO>> getInstitutionStudents(@PathVariable("id") int id){
		try{
			List<DefaultStudentDTO> students = institutionService.getInstitutionStudents(id, Pageable.unpaged());
			return new ResponseEntity<>(students, HttpStatus.OK);

		}catch(EntityNotFoundException e){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}

	@GetMapping(value = "/{id}/courses", produces = "application/json")
	public ResponseEntity<List<DefaultCourseDTO>> getInstitutionCourses(@PathVariable("id") int id){
		try{
			List<DefaultCourseDTO> courses = institutionService.getInstitutionCourses(id, Pageable.unpaged());
			return new ResponseEntity<>(courses, HttpStatus.OK);

		}catch(EntityNotFoundException e){ return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}

	@GetMapping(value = "/{id}/examPeriods", produces = "application/json")
	public ResponseEntity<List<DefaultExamPeriodDTO>> getInstitutionExamPeriods(@PathVariable("id") int id){
		try{
			List<DefaultExamPeriodDTO> examPeriods = institutionService.getInstitutionExamPeriods(id, Pageable.unpaged());
			return new ResponseEntity<>(examPeriods, HttpStatus.OK);

		}catch(EntityNotFoundException e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}
}
