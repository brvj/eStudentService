package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;


import java.util.Set;

@Entity
@Table(name = "teacher_title")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TeacherTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_title_id", unique = true, nullable = false)
    private Integer id;

    @NotBlank(message = "Teacher's title name is mandatory")
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacherTitle")
    private Set<Teacher> teachers;
}
