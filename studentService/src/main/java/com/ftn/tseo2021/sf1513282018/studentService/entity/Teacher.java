package com.ftn.tseo2021.sf1513282018.studentService.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "teachers")
@Data
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", nullable = false)
    private int id;
}
