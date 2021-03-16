package com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

public class ExamDTO implements Serializable{
    public int id;
    public LocalDateTime dateTime;

    public ExamDTO(){}

    public ExamDTO(Exam exam){
        this.id = exam.getId();
        this.dateTime = exam.getDateTime();
    }
}
