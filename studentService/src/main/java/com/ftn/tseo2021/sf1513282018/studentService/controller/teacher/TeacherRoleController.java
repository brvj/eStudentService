package com.ftn.tseo2021.sf1513282018.studentService.controller.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherRoleService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(value = "api/teacherRoles")
public class TeacherRoleController {
	
	@Autowired
	TeacherRoleService roleService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createTeacherRole(@NotNull @RequestBody DefaultTeacherRoleDTO teacherRoleDTO){
		int id = roleService.create(teacherRoleDTO);
		return new ResponseEntity<>(id, HttpStatus.CREATED);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateTeacherRole(@PathVariable("id") int id, @NotNull @RequestBody DefaultTeacherRoleDTO teacherRoleDTO){
		roleService.update(id, teacherRoleDTO);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteTeacherRole(@PathVariable("id") int id) throws PersonalizedAccessDeniedException {
		roleService.delete(id); return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultTeacherRoleDTO> getTeacherRoleById(@PathVariable("id") int id){
		DefaultTeacherRoleDTO teacherRoleDTO = roleService.getOne(id);
		return new ResponseEntity<>(teacherRoleDTO, HttpStatus.OK);
	}
}
