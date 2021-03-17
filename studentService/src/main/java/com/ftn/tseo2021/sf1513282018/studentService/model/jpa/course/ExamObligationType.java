package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "exam_obligation_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamObligationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_obligation_type_id", unique = true, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examObligationType")
    private Set<ExamObligation> examObligations;
}
