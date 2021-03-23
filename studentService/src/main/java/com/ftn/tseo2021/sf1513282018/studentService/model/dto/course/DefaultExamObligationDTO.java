package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamObligationTakingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultExamObligationDTO implements ExamObligationDTO {
    public int id;
    public int points;
    public String description;
    public DefaultExamObligationTypeDTO examObligationType;
    public DefaultCourseDTO course;
}
