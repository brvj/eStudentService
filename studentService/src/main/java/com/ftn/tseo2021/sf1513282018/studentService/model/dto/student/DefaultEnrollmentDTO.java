package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.EnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultEnrollmentDTO implements EnrollmentDTO {

	private static final long serialVersionUID = 4054742485346276995L;

	private Integer id;
	private LocalDate startDate;
	private boolean passed;
	private double score;
	private int grade;
	private DefaultStudentDTO student;
	private DefaultCourseDTO course;
	
}
