package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.FinancialCardDTO;
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
public class DefaultFinancialCardDTO implements FinancialCardDTO{

	private Integer id;
	private double currentAmmount;
	private double totalDeposit;
	private double totalSpent;
	
}
