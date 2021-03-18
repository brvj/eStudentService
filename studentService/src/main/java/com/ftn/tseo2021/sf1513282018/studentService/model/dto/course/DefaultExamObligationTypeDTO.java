package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamObligationTypeDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;

public class DefaultExamObligationTypeDTO implements ExamObligationTypeDTO{
    public int id;
    public String name;

    public DefaultExamObligationTypeDTO(){}

    public DefaultExamObligationTypeDTO(ExamObligationType examObligationType){
        this.id = examObligationType.getId();
        this.name = examObligationType.getName();
    }
}
