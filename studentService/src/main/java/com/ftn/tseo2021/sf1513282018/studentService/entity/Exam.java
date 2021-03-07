package com.ftn.tseo2021.sf1513282018.studentService.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exam")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", unique = true)
    private int id;

    @Column(name = "exam_date")
    private LocalDate date;

    public Exam (){}

    public Exam(int id, LocalDate date){
        this.id = id;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
