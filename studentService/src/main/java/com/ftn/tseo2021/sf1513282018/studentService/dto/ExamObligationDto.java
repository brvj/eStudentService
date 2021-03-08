package com.ftn.tseo2021.sf1513282018.studentService.dto;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

public class ExamObligationDto {
    public int id;
    public int points;
    public String description;

    public ExamObligationDto(){}

    public ExamObligationDto(ExamObligation examObligation){
        this.id = examObligation.getId();
        this.points = examObligation.getPoints();
        this.description = examObligation.getDescription();
    }
}
