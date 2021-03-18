package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

public class DefaultExamObligationDTO implements ExamObligationDTO {
    public int id;
    public int points;
    public String description;

    public DefaultExamObligationDTO(){}

    public DefaultExamObligationDTO(ExamObligation examObligation){
        this.id = examObligation.getId();
        this.points = examObligation.getPoints();
        this.description = examObligation.getDescription();
    }
}
