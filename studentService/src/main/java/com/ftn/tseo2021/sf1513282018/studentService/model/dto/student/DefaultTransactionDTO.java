package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.TransactionType;

public class DefaultTransactionDTO implements TransactionDTO {

	private Integer id;
	private double ammount;
	private LocalDate date;
	private String description;
	private TransactionType type;
	private DefaultFinancialCardDTO financialCard;
}
