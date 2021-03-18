package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", unique = true, nullable = false)
    private int id;

    @Column(name = "date")
    private LocalDateTime dateTime;

    @Column(name = "classroom")
    private String classroom;

    @ManyToOne
    @JoinColumn(name = "exam_period_id", referencedColumnName = "exam_period_id", nullable = false)
    private ExamPeriod examPeriod;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
    private Course course;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "exam")
    private Set<ExamTaking> examTakings;
    
}
