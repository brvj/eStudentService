package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.*;
import com.ftn.tseo2021.sf1513282018.studentService.security.CurrentPrincipal;
import com.ftn.tseo2021.sf1513282018.studentService.security.CustomPrincipal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping(value = "api/courses")
public class CourseController {
	
	@Autowired
	CourseService courseService;

	@PostMapping(consumes = "application/json")
	public ResponseEntity<Integer> createCourse(@RequestBody DefaultCourseDTO courseDTO) throws PersonalizedAccessDeniedException {
		try{
			int id = courseService.create(courseDTO);

			return new ResponseEntity<>(id, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public ResponseEntity<Void> updateCourse(@PathVariable("id") int id, @RequestBody DefaultCourseDTO courseDTO) throws PersonalizedAccessDeniedException {
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
	public ResponseEntity<Void> deleteCourse(@PathVariable("id") int id) throws PersonalizedAccessDeniedException {
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
		} catch (PersonalizedAccessDeniedException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping
	public ModelAndView getInstitutionCourses(@CurrentPrincipal CustomPrincipal principal) {
		return new ModelAndView(String.format("forward:/api/institutions/%d/courses", principal.getInstitutionId()));
	}

	@GetMapping(value = "/{id}/teachings", produces = "application/json")
	public ResponseEntity<List<CourseTeachingDTO>> getCourseTeachings(@PathVariable("id") int id){
		List<CourseTeachingDTO> courseTeachingDTOList;

		courseTeachingDTOList = courseService.getCourseTeachings(id, Pageable.unpaged());
		if(courseTeachingDTOList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(courseTeachingDTOList, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/enrollments", produces = "application/json")
	public ResponseEntity<List<CourseEnrollmentDTO>> getCourseEnrollments(@PathVariable("id") int id){
		List<CourseEnrollmentDTO> courseEnrollmentDTOList;

		courseEnrollmentDTOList = courseService.getCourseEnrollments(id, Pageable.unpaged());
		if(courseEnrollmentDTOList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(courseEnrollmentDTOList, HttpStatus.OK);
	}

	@GetMapping(value = "/{id}/examObligations", produces = "application/json")
	public ResponseEntity<List<CourseExamObligationDTO>> getCourseExamObligations(@PathVariable("id") int id){
		try{
			List<CourseExamObligationDTO> courseExamObligationDTOList = courseService.getCourseExamObligations(id, Pageable.unpaged());
			return new ResponseEntity<>(courseExamObligationDTOList, HttpStatus.OK);
		}
		catch (PersonalizedAccessDeniedException e){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/{id}/exams", produces = "application/json")
	public ResponseEntity<List<CourseExamDTO>> getCourseExams(@PathVariable("id") int id){
		List<CourseExamDTO> courseExamDTOList;

		courseExamDTOList = courseService.getCourseExams(id, Pageable.unpaged());
		if(courseExamDTOList == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(courseExamDTOList, HttpStatus.OK);
	}
}
