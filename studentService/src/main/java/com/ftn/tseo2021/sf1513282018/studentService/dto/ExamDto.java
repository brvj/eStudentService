package com.ftn.tseo2021.sf1513282018.studentService.dto;

import com.ftn.tseo2021.sf1513282018.studentService.entity.Exam;

import java.time.LocalDate;

public class ExamDto {
    public int id;
    public LocalDate date;

    public ExamDto(){}

    public ExamDto(Exam exam){
        this.id = exam.getId();
        this.date = exam.getDate();
    }
}
