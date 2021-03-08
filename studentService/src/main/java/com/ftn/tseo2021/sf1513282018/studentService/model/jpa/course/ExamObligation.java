package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "exam_obligation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamObligation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_obligation_id", unique = true, nullable = false)
    private int id;

    @Column(name = "points")
    private int points;

    @Column(name = "description")
    private String description;

}
