package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table(name = "teacher_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_role_id", unique = true, nullable = false)
    private int id;

    @NotBlank(message = "Teacher's role name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacherRole")
    private Set<Teaching> teachings;
}
