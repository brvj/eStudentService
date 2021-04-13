package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseExamObligationDTO implements ExamObligationDTO {

	private static final long serialVersionUID = -8740949250879763280L;
	
	public int id;
    public int points;
    public String description;
    public DefaultExamObligationTypeDTO examObligationType;

}
