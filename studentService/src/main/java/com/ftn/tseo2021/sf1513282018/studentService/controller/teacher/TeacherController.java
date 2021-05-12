package com.ftn.tseo2021.sf1513282018.studentService.controller.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.TeacherTeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.CurrentPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(value = "api/teachers")
public class TeacherController {
	
	@Autowired
	TeacherService teacherService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createTeacher(@NotNull @RequestBody DefaultTeacherDTO teacherDTO){
		try{
			int teacherId = teacherService.create(teacherDTO);
			return new ResponseEntity<>(teacherId, HttpStatus.CREATED);

		}catch(IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (ForbiddenAccessException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateTeacher(@PathVariable("id") int id, @NotNull @RequestBody DefaultTeacherDTO teacherDTO){
		try{
			teacherService.update(id, teacherDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}catch(EntityNotFoundException | ForbiddenAccessException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTeacher(@PathVariable("id") int id) throws ForbiddenAccessException {
		if(teacherService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeacherDTO> getTeacherById(@PathVariable("id") int id){
		DefaultTeacherDTO teacherDTO;
		try {
			teacherDTO = teacherService.getOne(id);
			if(teacherDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(teacherDTO, HttpStatus.OK);
		} catch (ForbiddenAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/user/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeacherDTO> getTeacherByUserId(@PathVariable("id") int id){
		try{
			DefaultTeacherDTO teacherDTO = teacherService.getByUserId(id, Pageable.unpaged());
			return new ResponseEntity<>(teacherDTO, HttpStatus.OK);

		}catch(EntityNotFoundException e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}

	@GetMapping(value = "/{id}/teachings", produces = "application/json")
	public ResponseEntity<List<TeacherTeachingDTO>> getTeacherTeachings(@PathVariable("id") int id){
		try{
			List<TeacherTeachingDTO> teachings = teacherService.getTeacherTeachings(id, Pageable.unpaged());
			return new ResponseEntity<>(teachings, HttpStatus.OK);

		}catch(EntityNotFoundException e){return new ResponseEntity<>(HttpStatus.NOT_FOUND);}
	}
	
	@GetMapping
	public ModelAndView getInstitutionTeachers(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/teachers", principal.getInstitutionId()));
	}
}
