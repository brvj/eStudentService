package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "teacher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id", unique = true, nullable = false)
    private int id;

    @ManyToOne
    @JoinColumn(name = "teacher_title_id", referencedColumnName = "teacher_title_id", nullable = false)
    private TeacherTitle teacherTitle;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "teacher")
    private Teaching teacherTeachingCourse;

    @ManyToOne
    @JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
    private Institution institution;
}
