package com.ftn.tseo2021.sf1513282018.studentService.controller.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.student.FinancialCardService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/financialCards")
public class FinancialCardController {
	
	@Autowired
	FinancialCardService cardService;

}
