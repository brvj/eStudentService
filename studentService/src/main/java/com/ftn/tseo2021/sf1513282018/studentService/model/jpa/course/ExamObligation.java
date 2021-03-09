package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;

import java.util.Set;

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
    
    @ManyToOne
    @JoinColumn(name = "exam_obligation_type_id", referencedColumnName = "exam_obligation_type_id", nullable = false)
    private ExamObligationType examObligationType;
    
    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examObligation")
    private Set<ExamObligationTaking> examObligationTaking;
}
