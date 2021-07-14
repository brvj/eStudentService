package com.ftn.tseo2021.sf1513282018.studentService.controller.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherTitleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherTitleService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "api/teacherTitles")
public class TeacherTitleController {
	
	@Autowired
	TeacherTitleService titleService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createTeacherTitle(@NotNull @RequestBody DefaultTeacherTitleDTO teacherTitleDTO){
		int id = titleService.create(teacherTitleDTO);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateTeacherTitle(@PathVariable("id") int id, @NotNull @RequestBody DefaultTeacherTitleDTO teacherTitleDTO){
		titleService.update(id, teacherTitleDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTeacherTitle(@PathVariable("id") int id) throws PersonalizedAccessDeniedException {
		titleService.delete(id); return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeacherTitleDTO> getTeacherTitleById(@PathVariable("id") int id){
		DefaultTeacherTitleDTO teacherTitleDTO = titleService.getOne(id);
		return new ResponseEntity<>(teacherTitleDTO, HttpStatus.OK);
	}

	@GetMapping(produces = "application/json")
	public ResponseEntity<Page<DefaultTeacherTitleDTO>> getAll(){
		Page<DefaultTeacherTitleDTO> titles = titleService.getAll(Pageable.unpaged());
		return new ResponseEntity<>(titles, HttpStatus.OK);
	}
}
