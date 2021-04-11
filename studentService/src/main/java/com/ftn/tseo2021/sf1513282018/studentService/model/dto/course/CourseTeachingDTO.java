package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherRoleDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CourseTeachingDTO implements TeachingDTO {

	private static final long serialVersionUID = -7989257937289633535L;
	
	private Integer id;
    private LocalDate startDate;
    private DefaultTeacherDTO teacher;
    private DefaultTeacherRoleDTO teacherRole;

}
