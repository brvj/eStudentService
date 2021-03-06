package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enrollment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enrollment_id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "passed")
	private boolean passed;
	
	@Column(name = "score")
	private double score;
	
	@Column(name = "grade")
	private int grade;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
	private Course course;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "enrollment")
	private Set<ExamObligationTaking> examObligationTakings;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "enrollment")
	private Set<ExamTaking> examTakings;

}
