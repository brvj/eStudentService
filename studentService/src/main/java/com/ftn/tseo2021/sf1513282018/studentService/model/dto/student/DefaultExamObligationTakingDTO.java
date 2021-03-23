package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamObligationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultExamObligationTakingDTO implements ExamObligationTakingDTO{

	private static final long serialVersionUID = 2029934122026836573L;

	
	private Integer id;
	private double score;
	private DefaultEnrollmentDTO enrollment;
	private DefaultExamObligationDTO examObligation;
	
}
