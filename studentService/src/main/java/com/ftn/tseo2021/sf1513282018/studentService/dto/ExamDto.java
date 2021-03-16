package com.ftn.tseo2021.sf1513282018.studentService.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

public class ExamDto {
    public int id;
    public LocalDateTime dateTime;

    public ExamDto(){}

    public ExamDto(Exam exam){
        this.id = exam.getId();
        this.dateTime = exam.getDateTime();
    }
}
