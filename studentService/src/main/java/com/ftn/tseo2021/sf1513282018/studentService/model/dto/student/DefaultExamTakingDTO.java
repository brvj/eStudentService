package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultExamTakingDTO implements ExamTakingDTO{
	
	private static final long serialVersionUID = 8080756214072254209L;
	
	private Integer id;
	private double score;
	private DefaultEnrollmentDTO enrollment;
	private DefaultExamDTO exam;

}
