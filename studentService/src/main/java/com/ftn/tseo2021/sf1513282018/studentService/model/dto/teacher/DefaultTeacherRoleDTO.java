package com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultTeacherRoleDTO implements TeacherRoleDTO {

	private static final long serialVersionUID = -4821933542961734979L;
	
	private Integer id;
    private String name;
}
