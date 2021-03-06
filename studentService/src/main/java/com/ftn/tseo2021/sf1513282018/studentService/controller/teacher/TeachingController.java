package com.ftn.tseo2021.sf1513282018.studentService.controller.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeachingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeachingService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "api/teachings")
public class TeachingController {
	
	@Autowired
	TeachingService teachingService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createTeaching(@NotNull @RequestBody DefaultTeachingDTO teachingDTO){
		int teaching = teachingService.create(teachingDTO);
		return new ResponseEntity<>(teaching, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateTeaching(@PathVariable("id") int id, @NotNull @RequestBody DefaultTeachingDTO teachingDTO){
		teachingService.update(id, teachingDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTeaching(@PathVariable("id") int id) throws PersonalizedAccessDeniedException {
		teachingService.delete(id); return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeachingDTO> getTeachingById(@PathVariable("id") int id){
		DefaultTeachingDTO teachingDTO = teachingService.getOne(id);
		return new ResponseEntity<>(teachingDTO, HttpStatus.OK);
	}
}
