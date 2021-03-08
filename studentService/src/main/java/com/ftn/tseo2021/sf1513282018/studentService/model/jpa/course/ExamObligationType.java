package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import javax.persistence.*;

@Entity
@Table(name = "exam_obligation_type")
public class ExamObligationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_obligation_type_id", unique = true, nullable = false)
    private int id;

    @Column(name = "name")
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
