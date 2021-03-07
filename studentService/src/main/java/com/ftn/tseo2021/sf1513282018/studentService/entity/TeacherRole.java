package com.ftn.tseo2021.sf1513282018.studentService.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "teacher_roles")
@Data
public class TeacherRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_role_id", nullable = false)
    private int id;

    @NotBlank(message = "Teacher's role name is mandatory")
    @Column(name = "role_name", nullable = false)
    private String name;
}
