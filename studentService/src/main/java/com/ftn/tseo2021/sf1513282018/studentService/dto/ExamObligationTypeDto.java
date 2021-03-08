package com.ftn.tseo2021.sf1513282018.studentService.dto;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;

public class ExamObligationTypeDto {
    public int id;
    public String name;

    public ExamObligationTypeDto(){}

    public ExamObligationTypeDto(ExamObligationType examObligationType){
        this.id = examObligationType.getId();
        this.name = examObligationType.getName();
    }
}
