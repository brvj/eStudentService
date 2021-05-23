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

import javax.validation.constraints.NotNull;

import java.util.List;

@RestController
@RequestMapping(value = "api/institutions")
public class InstitutionController {
	
	@Autowired
	private InstitutionService institutionService;
	
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createInstitution(@NotNull @RequestBody DefaultInstitutionDTO institutionDTO) {
		int institutionId = institutionService.create(institutionDTO);
		return new ResponseEntity<>(institutionId, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateInstitution(@PathVariable("id") int id, @NotNull @RequestBody DefaultInstitutionDTO institutionDTO) {
		institutionService.update(id, institutionDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteInstitution(@PathVariable("id") int id) {
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
		List<InstitutionUserDTO> users = institutionService.getInstitutionUsers(id, Pageable.unpaged());
		return new ResponseEntity<>(users, HttpStatus.OK);

	}
	
	@GetMapping(value = "/{id}/admins", produces = "application/json")
	public ResponseEntity<Page<InstitutionUserDTO>> getInstitutionAdmins(@PathVariable("id") int id, Pageable pageable) {
		Page<InstitutionUserDTO> admins = institutionService.getInstitutionAdmins(id, pageable);
		return new ResponseEntity<>(admins, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/teachers", produces = "application/json")
	public ResponseEntity<List<InstitutionTeacherDTO>> getInstitutionTeachers(@PathVariable("id") int id){
		List<InstitutionTeacherDTO> teachers = institutionService.getInstitutionTeachers(id, Pageable.unpaged());
		return new ResponseEntity<>(teachers, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/students", produces = "application/json")
	public ResponseEntity<List<InstitutionStudentDTO>> getInstitutionStudents(@PathVariable("id") int id){
		List<InstitutionStudentDTO> students = institutionService.getInstitutionStudents(id, Pageable.unpaged());
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/courses", produces = "application/json")
	public ResponseEntity<List<InstitutionCourseDTO>> getInstitutionCourses(@PathVariable("id") int id){
		List<InstitutionCourseDTO> courses = institutionService.getInstitutionCourses(id, Pageable.unpaged());
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/examPeriods", produces = "application/json")
	public ResponseEntity<Page<InstitutionExamPeriodDTO>> getInstitutionExamPeriods(@PathVariable("id") int id, Pageable pageable){
		Page<InstitutionExamPeriodDTO> examPeriods = institutionService.getInstitutionExamPeriods(id, pageable);
		return new ResponseEntity<>(examPeriods, HttpStatus.OK);
	}
}
