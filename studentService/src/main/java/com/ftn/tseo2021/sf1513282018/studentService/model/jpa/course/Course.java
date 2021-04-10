package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import java.util.Set;

@Entity
@Table(name = "course")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
	private Institution institution;

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "course")
	private Set<Teaching> teachings;

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "course")
	private Set<Exam> exams;

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "course")
	private Set<ExamObligation> examObligations;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "course")
	private Set<Enrollment> enrollments;
}
