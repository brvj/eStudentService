package com.ftn.tseo2021.sf1513282018.studentService.entity;

import javax.persistence.*;

@Entity
@Table(name = "examObligation")
public class ExamObligation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "examObligation_id", unique = true)
    private int id;

    @Column(name = "examObligation_points")
    private int points;

    @Column(name = "examObligation_description")
    private String description;

    public ExamObligation(){}

    public ExamObligation(int id, int points, String description){
        this.id = id;
        this.points = points;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
