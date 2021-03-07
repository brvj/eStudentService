package com.ftn.tseo2021.sf1513282018.studentService.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "institutions")
@Data
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id")
    private int id;

    @NotBlank(message = "Institution name is mandatory")
    @Column(name = "institution_name", nullable = false)
    private String name;
}
