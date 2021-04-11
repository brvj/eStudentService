package com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeacherTeachingDTO implements TeachingDTO {

	private static final long serialVersionUID = 8774814829162084475L;
	
	private Integer id;
    private LocalDate startDate;
    private DefaultTeacherRoleDTO teacherRole;
    private DefaultCourseDTO course;

}
