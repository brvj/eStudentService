package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student;

import java.time.LocalDate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StudentFilterOptions {
	
	public String firstName;
	public String lastName;
	public String studentCard;
	public String address;
	public Integer generationFrom;
	public Integer generationTo;
	public LocalDate dateOfBirthFrom;
	public LocalDate dateOfBirthTo;
	public String username;
	public String email;
	public String phoneNumber;

}
