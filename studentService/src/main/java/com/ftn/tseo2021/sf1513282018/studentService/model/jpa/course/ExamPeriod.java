package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "exam_period")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamPeriod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_period_id", unique = true, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
    private Institution institution;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "examPeriod")
    private Set<Exam> exams;
}
