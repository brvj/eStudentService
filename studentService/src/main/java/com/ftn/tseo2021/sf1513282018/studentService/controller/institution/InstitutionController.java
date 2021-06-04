package com.ftn.tseo2021.sf1513282018.studentService.controller.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.course.ExamPeriodFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherFilterOptions;
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

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.user.user.UserFilterOptions;
import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;

import javax.validation.constraints.NotNull;

import java.time.LocalDate;

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
	public ResponseEntity<Page<InstitutionUserDTO>> getInstitutionUsers(@PathVariable("id") int id, Pageable pageable) {
		Page<InstitutionUserDTO> users = institutionService.getInstitutionUsers(id, pageable);
		return new ResponseEntity<>(users, HttpStatus.OK);

	}
	
	@GetMapping(value = "/{id}/admins", produces = "application/json")
	public ResponseEntity<Page<InstitutionUserDTO>> getInstitutionAdmins(@PathVariable("id") int id, Pageable pageable, 
			@RequestParam(name = "firstName", required = false) String firstName, 
			@RequestParam(name = "lastName", required = false) String lastName, 
			@RequestParam(name = "username", required = false) String username, 
			@RequestParam(name = "email", required = false) String email, 
			@RequestParam(name = "phoneNumber", required = false) String phoneNumber
			) {
		
		UserFilterOptions filterOptions = new UserFilterOptions(username, firstName, lastName, email, phoneNumber);
		Page<InstitutionUserDTO> admins = institutionService.getInstitutionAdmins(id, pageable, filterOptions);
		return new ResponseEntity<>(admins, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/teachers", produces = "application/json")
	public ResponseEntity<Page<InstitutionTeacherDTO>> getInstitutionTeachers(@PathVariable("id") int id, Pageable pageable,
																			  @RequestParam(name = "firstName", required = false) String firstName,
																			  @RequestParam(name = "lastName", required = false) String lastName,
																			  @RequestParam(name = "address", required = false) String address,
																			  @RequestParam(name = "teacherTitleName", required = false) String teacherTitleName,
																			  @RequestParam(name = "dateOfBirthFrom", required = false) String dateOfBirthFrom,
																			  @RequestParam(name = "dateOfBirthTo", required = false) String dateOfBirthTo,
																			  @RequestParam(name = "username", required = false) String username,
																			  @RequestParam(name = "email", required = false) String email,
																			  @RequestParam(name = "phoneNumber", required = false) String phoneNumber){
		LocalDate dateFrom = null;
		LocalDate dateTo = null;
		try {
			dateFrom = LocalDate.parse(dateOfBirthFrom);
		} catch (Exception e) {}
		try {
			dateTo = LocalDate.parse(dateOfBirthTo);
		} catch (Exception e) {}

		TeacherFilterOptions filterOptions = new TeacherFilterOptions(firstName, lastName, address, teacherTitleName, dateFrom, dateTo, username, email, phoneNumber);
		Page<InstitutionTeacherDTO> teachers = institutionService.getInstitutionTeachers(id, pageable, filterOptions);
		return new ResponseEntity<>(teachers, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/students", produces = "application/json")
	public ResponseEntity<Page<InstitutionStudentDTO>> getInstitutionStudents(@PathVariable("id") int id, Pageable pageable, 
			@RequestParam(name = "firstName", required = false) String firstName, 
			@RequestParam(name = "lastName", required = false) String lastName, 
			@RequestParam(name = "studentCard", required = false) String studentCard, 
			@RequestParam(name = "address", required = false) String address, 
			@RequestParam(name = "generationFrom", required = false) Integer generationFrom, 
			@RequestParam(name = "generationTo", required = false) Integer generationTo,
			@RequestParam(name = "dateOfBirthFrom", required = false) String dateOfBirthFromString, 
			@RequestParam(name = "dateOfBirthTo", required = false) String dateOfBirthToString, 
			@RequestParam(name = "username", required = false) String username, 
			@RequestParam(name = "email", required = false) String email, 
			@RequestParam(name = "phoneNumber", required = false) String phoneNumber
			){
		
		LocalDate dateOfBirthFrom = null;
		LocalDate dateOfBirthTo = null;
		try {
			dateOfBirthFrom = LocalDate.parse(dateOfBirthFromString);
		} catch (Exception e) {}
		try {
			dateOfBirthTo = LocalDate.parse(dateOfBirthToString);
		} catch (Exception e) {}
		
		StudentFilterOptions filterOptions = new StudentFilterOptions(firstName, lastName, studentCard, address, generationFrom, 
				generationTo, dateOfBirthFrom, dateOfBirthTo, username, email, phoneNumber);
		
		Page<InstitutionStudentDTO> students = institutionService.getInstitutionStudents(id, pageable, filterOptions);
		return new ResponseEntity<>(students, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/courses", produces = "application/json")
	public ResponseEntity<Page<InstitutionCourseDTO>> getInstitutionCourses(@PathVariable("id") int id, Pageable pageable){
		Page<InstitutionCourseDTO> courses = institutionService.getInstitutionCourses(id, pageable);
		return new ResponseEntity<>(courses, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/examPeriods", produces = "application/json")
	public ResponseEntity<Page<InstitutionExamPeriodDTO>> getInstitutionExamPeriods(@PathVariable("id") int id, Pageable pageable,
																					@RequestParam(name = "name", required = false) String name,
																					@RequestParam(name = "startDate", required = false) String startDate,
																					@RequestParam(name = "endDate", required = false) String endDate){
		LocalDate start = (startDate == null || startDate.isEmpty()) ? null : LocalDate.parse(startDate);
		LocalDate end = (endDate == null || endDate.isEmpty()) ? null : LocalDate.parse(endDate);
		ExamPeriodFilterOptions filterOptions = new ExamPeriodFilterOptions(name, start, end);
		Page<InstitutionExamPeriodDTO> examPeriods = institutionService.getInstitutionExamPeriods(id, pageable, filterOptions);
		return new ResponseEntity<>(examPeriods, HttpStatus.OK);
	}
}
