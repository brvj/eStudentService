package com.ftn.tseo2021.sf1513282018.studentService.controller.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.annotations.CurrentPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "api/teachers")
public class TeacherController {
	
	@Autowired
	TeacherService teacherService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createTeacher(@NotNull @RequestBody DefaultTeacherDTO teacherDTO){
		int teacherId = teacherService.create(teacherDTO);
		return new ResponseEntity<>(teacherId, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateTeacher(@PathVariable("id") int id, @NotNull @RequestBody DefaultTeacherDTO teacherDTO){
		teacherService.update(id, teacherDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTeacher(@PathVariable("id") int id) {
		teacherService.delete(id); return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeacherDTO> getTeacherById(@PathVariable("id") int id){
		DefaultTeacherDTO teacherDTO = teacherService.getOne(id);
		return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/user/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeacherDTO> getTeacherByUserId(@PathVariable("id") int id){
		DefaultTeacherDTO teacherDTO = teacherService.getByUserId(id);
		return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/teachings", produces = "application/json")
	public ResponseEntity<Page<TeacherTeachingDTO>> getTeacherTeachings(@PathVariable("id") int id, Pageable pageable){
		Page<TeacherTeachingDTO> teachings = teacherService.getTeacherTeachings(id, pageable);
		return new ResponseEntity<>(teachings, HttpStatus.OK);
	}
	
	@GetMapping
	public ModelAndView getInstitutionTeachers(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/teachers", principal.getInstitutionId()));
	}
}
