package com.ftn.tseo2021.sf1513282018.studentService.controller.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.course.CourseService;

@RestController
@RequestMapping(value = "api/courses")
public class CourseController {
	
	@Autowired
	CourseService courseService;

}
