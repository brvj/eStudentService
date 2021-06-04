package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.StudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstitutionStudentDTO implements StudentDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String studentCard;
	private String address;
	private int generation;
	private LocalDate dateOfBirth;
	private DefaultUserDTO user;

}
