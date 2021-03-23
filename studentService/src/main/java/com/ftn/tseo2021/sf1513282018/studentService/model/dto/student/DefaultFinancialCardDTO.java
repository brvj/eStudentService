package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;

public class DefaultFinancialCardDTO implements FinancialCardDTO{

	private Integer id;
	private double currentAmmount;
	private double totalDeposit;
	private double totalSpent;
	
}
