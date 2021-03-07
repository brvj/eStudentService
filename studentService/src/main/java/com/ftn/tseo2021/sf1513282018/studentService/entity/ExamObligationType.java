package com.ftn.tseo2021.sf1513282018.studentService.entity;

import javax.persistence.*;

@Entity
@Table(name = "examObligationType")
public class ExamObligationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examObligationType_id", unique = true)
    private int id;

    @Column(name = "examObligationType_name")
    private String name;

    public ExamObligationType(){}

    public ExamObligationType(int id, String name){
        this.id = id;
        this.name = name;
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
}
