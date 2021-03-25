package com.ftn.tseo2021.sf1513282018.studentService.controller.institution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.institution.InstitutionService;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/institutions")
public class InstitutionController {
	
	@Autowired
	InstitutionService institutionService;

}
