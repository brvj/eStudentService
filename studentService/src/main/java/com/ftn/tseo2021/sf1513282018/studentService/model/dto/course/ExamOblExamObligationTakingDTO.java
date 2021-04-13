package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.ExamObligationTakingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamOblExamObligationTakingDTO implements ExamObligationTakingDTO {

	private static final long serialVersionUID = 5884402997624933268L;
	
	private Integer id;
	private double score;
	private DefaultEnrollmentDTO enrollment;

}
