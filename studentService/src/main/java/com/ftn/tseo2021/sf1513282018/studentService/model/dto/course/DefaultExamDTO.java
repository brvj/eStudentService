package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

public class DefaultExamDTO implements ExamDTO{
    public int id;
    public LocalDateTime dateTime;

    public DefaultExamDTO(){}

    public DefaultExamDTO(Exam exam){
        this.id = exam.getId();
        this.dateTime = exam.getDateTime();
    }
}
