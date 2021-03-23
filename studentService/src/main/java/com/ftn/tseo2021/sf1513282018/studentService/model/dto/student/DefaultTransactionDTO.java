package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.TransactionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.common.TransactionType;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultTransactionDTO implements TransactionDTO {

	private Integer id;
	private double ammount;
	private LocalDate date;
	private String description;
	private TransactionType type;
	private DefaultFinancialCardDTO financialCard;
}
