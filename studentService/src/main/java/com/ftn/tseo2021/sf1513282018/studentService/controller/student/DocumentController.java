package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.DocumentService;

@RestController
@RequestMapping(value = "api/documents")
public class DocumentController {
	
	@Autowired
	DocumentService documentService;

}
