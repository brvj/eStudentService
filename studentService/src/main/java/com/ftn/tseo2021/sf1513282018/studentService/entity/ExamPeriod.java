package com.ftn.tseo2021.sf1513282018.studentService.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "examPeriod")
public class ExamPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examPeriod_id")
    private int id;

    @Column(name = "examPeriod_name")
    private String name;

    @Column(name = "examPeriod_startDate")
    private LocalDate startDate;

    @Column(name = "examPeriod_endDate")
    private LocalDate endDate;

    public ExamPeriod(){}

    public ExamPeriod(int id, String name, LocalDate startDate, LocalDate endDate){
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
