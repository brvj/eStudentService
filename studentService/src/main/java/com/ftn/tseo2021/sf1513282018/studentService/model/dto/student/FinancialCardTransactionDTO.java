package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinancialCardTransactionDTO implements TransactionDTO {

	private static final long serialVersionUID = -6386426933595054639L;
	
	private Integer id;
	private double ammount;
	private LocalDate date;
	private String description;
	private TransactionType type;

}
