package com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class InstitutionTeacherDTO implements TeacherDTO {

	private static final long serialVersionUID = -4864878291792529041L;
	
	private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate dateOfBirth;
    private DefaultTeacherTitleDTO teacherTitle;

}
