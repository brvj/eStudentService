package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "exam_obligation_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamObligationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_obligation_type_id", unique = true, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "exam_obligation_id", referencedColumnName = "exam_obligation_id", nullable = false)
    private ExamObligation examObligation;
}
