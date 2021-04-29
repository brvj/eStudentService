package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.security.CurrentPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.ForbiddenAccessException;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping(value = "api/courses")
public class CourseController {
	
	@Autowired
	CourseService courseService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createCourse(@RequestBody DefaultCourseDTO courseDTO){
		try{
			int id = courseService.create(courseDTO);

			return new ResponseEntity<>(id, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateCourse(@PathVariable("id") int id, @RequestBody DefaultCourseDTO courseDTO){
		try{
			courseService.update(id, courseDTO);

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch (EntityNotFoundException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable("id") int id){
		if(courseService.delete(id)) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity<DefaultCourseDTO> getCourseById(@PathVariable("id") int id){
		DefaultCourseDTO courseDTO;
		try {
			courseDTO = courseService.getOne(id);
			if(courseDTO == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return new ResponseEntity<>(courseDTO, HttpStatus.OK);
		} catch (ForbiddenAccessException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ModelAndView getInstitutionCourses(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/courses", principal.getInstitutionId()));
	}
}
