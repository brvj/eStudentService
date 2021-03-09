package com.ftn.tseo2021.sf1513282018.studentService.dto;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

public class ExamDto {
    public int id;
    public LocalDate date;

    public ExamDto(){}

    public ExamDto(Exam exam){
        this.id = exam.getId();
    }
}
