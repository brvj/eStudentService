package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import java.time.LocalDateTime;
import java.util.Set;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultExamTakingDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultExamDTO implements ExamDTO{
    public int id;
    public LocalDateTime dateTime;
    public DefaultCourseDTO course;
    public String description;
    public String classroom;
    public int points;
    public DefaultExamPeriodDTO examPeriod;
}
