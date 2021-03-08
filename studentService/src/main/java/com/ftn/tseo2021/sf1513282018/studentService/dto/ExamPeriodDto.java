package com.ftn.tseo2021.sf1513282018.studentService.dto;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;

public class ExamPeriodDto {
    public int id;
    public String name;
    public LocalDate startDate;
    public LocalDate endDate;

    public ExamPeriodDto(){}

    public ExamPeriodDto(ExamPeriod examPeriod){
        this.id = examPeriod.getId();
        this.name = examPeriod.getName();
        this.startDate = examPeriod.getStartDate();
        this.endDate = examPeriod.getEndDate();
    }
}
