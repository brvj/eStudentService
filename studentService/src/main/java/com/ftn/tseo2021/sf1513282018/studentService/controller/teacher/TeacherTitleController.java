package com.ftn.tseo2021.sf1513282018.studentService.controller.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.teacher.TeacherTitleService;

@RestController
@RequestMapping(value = "api/teacherTitles")
public class TeacherTitleController {
	
	@Autowired
	TeacherTitleService titleService;

}
