package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "teacherTitle")
    private Set<Teacher> teachers;
}
