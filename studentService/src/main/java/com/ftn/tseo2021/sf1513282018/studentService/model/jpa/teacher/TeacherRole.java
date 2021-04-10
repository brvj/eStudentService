package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "teacherRole")
    private Set<Teaching> teachings;
}
