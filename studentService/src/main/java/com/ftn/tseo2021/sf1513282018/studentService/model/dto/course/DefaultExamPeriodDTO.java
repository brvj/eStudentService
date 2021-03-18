package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import java.time.LocalDate;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.ExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;

public class DefaultExamPeriodDTO implements ExamPeriodDTO{
    public int id;
    public String name;
    public LocalDate startDate;
    public LocalDate endDate;

    public DefaultExamPeriodDTO(){}

    public DefaultExamPeriodDTO(ExamPeriod examPeriod){
        this.id = examPeriod.getId();
        this.name = examPeriod.getName();
        this.startDate = examPeriod.getStartDate();
        this.endDate = examPeriod.getEndDate();
    }
}
