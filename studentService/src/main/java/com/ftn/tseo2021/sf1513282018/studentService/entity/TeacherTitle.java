package com.ftn.tseo2021.sf1513282018.studentService.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "teacher_titles")
@Data
public class TeacherTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_title_id", nullable = false)
    private int id;

    @NotBlank(message = "Teacher's title name is mandatory")
    @Column(name = "title_name", nullable = false)
    private String title;
}
