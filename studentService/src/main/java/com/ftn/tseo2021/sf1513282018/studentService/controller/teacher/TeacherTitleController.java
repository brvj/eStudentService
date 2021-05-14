package com.ftn.tseo2021.sf1513282018.studentService.controller.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherTitleService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "api/teacherTitles")
public class TeacherTitleController {
	
	@Autowired
	TeacherTitleService titleService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createTeacherTitle(@NotNull @RequestBody DefaultTeacherTitleDTO teacherTitleDTO){
		try{
			int id = titleService.create(teacherTitleDTO);
			return new ResponseEntity<>(id, HttpStatus.CREATED);

		}catch(IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}catch (PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateTeacherTitle(@PathVariable("id") int id, @NotNull @RequestBody DefaultTeacherTitleDTO teacherTitleDTO){
		try{
			titleService.update(id, teacherTitleDTO);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		}catch(EntityNotFoundException | PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTeacherTitle(@PathVariable("id") int id) throws PersonalizedAccessDeniedException {
		titleService.delete(id); return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeacherTitleDTO> getTeacherTitleById(@PathVariable("id") int id){
		DefaultTeacherTitleDTO teacherTitleDTO;
		try {
			teacherTitleDTO = titleService.getOne(id);
			
			if(teacherTitleDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(teacherTitleDTO, HttpStatus.OK);
		} catch (PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
