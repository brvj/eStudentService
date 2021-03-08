package com.ftn.tseo2021.sf1513282018.studentService.model.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "institution")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Institution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "institution_id", unique = true, nullable = false)
    private int id;

    @NotBlank(message = "Institution name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;
}
