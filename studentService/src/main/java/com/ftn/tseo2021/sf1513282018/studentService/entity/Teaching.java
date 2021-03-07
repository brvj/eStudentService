package com.ftn.tseo2021.sf1513282018.studentService.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "teacher_teaching")
@Data
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_id", nullable = false)
    private int id;
}
