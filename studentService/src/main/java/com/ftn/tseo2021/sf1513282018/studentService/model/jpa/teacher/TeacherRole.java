package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.util.Set;

@Entity
@Table(name = "teacher_role")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_role_id", unique = true, nullable = false)
    private Integer id;

    @NotBlank(message = "Teacher's role name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacherRole")
    private Set<Teaching> teachings;
}
