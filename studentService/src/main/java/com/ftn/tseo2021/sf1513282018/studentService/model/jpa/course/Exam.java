package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exam")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exam {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exam_id", unique = true, nullable = false)
    private int id;

    @Column(name = "date")
    private LocalDate date;
}
