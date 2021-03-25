package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import org.springframework.beans.factory.annotation.Autowired;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.StudentService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/students")
public class StudentController {
	
	@Autowired
	StudentService studentService;

}
