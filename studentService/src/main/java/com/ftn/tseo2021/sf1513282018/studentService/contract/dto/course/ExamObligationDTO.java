package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course;

import java.io.Serializable;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

public class ExamObligationDTO implements Serializable {
    public int id;
    public int points;
    public String description;

    public ExamObligationDTO(){}

    public ExamObligationDTO(ExamObligation examObligation){
        this.id = examObligation.getId();
        this.points = examObligation.getPoints();
        this.description = examObligation.getDescription();
    }
}
