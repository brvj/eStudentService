package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import java.time.LocalDateTime;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExamPeriodExamDTO implements ExamDTO {

	private static final long serialVersionUID = -8142964718991037513L;
	
	public int id;
    public LocalDateTime dateTime;
    public DefaultCourseDTO course;
    public String description;
    public String classroom;
    public int points;

}
