package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import javax.persistence.*;

@Entity
@Table(name = "exam_obligation")
public class ExamObligation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_obligation_id", unique = true, nullable = false)
    private int id;

    @Column(name = "points")
    private int points;

    @Column(name = "description")
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
