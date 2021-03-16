package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course;

import java.io.Serializable;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;

public class ExamObligationTypeDTO implements Serializable{
    public int id;
    public String name;

    public ExamObligationTypeDTO(){}

    public ExamObligationTypeDTO(ExamObligationType examObligationType){
        this.id = examObligationType.getId();
        this.name = examObligationType.getName();
    }
}
