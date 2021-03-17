package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;

@Entity
@Table(name = "teacher_teaching_course")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teaching {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teaching_id", unique = true, nullable = false)
    private int id;
    
    @Column(name = "start_date")
	private LocalDate startDate;

    @ManyToOne
    @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(name = "teacher_role_id", referencedColumnName = "teacher_role_id", nullable = false)
    private TeacherRole teacherRole;

    @ManyToOne
    @JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
    private Course course;
}
