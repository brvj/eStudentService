package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "enrollment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Enrollment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "enrollment_id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "start_date")
	private LocalDate startDate;
	
	@Column(name = "passed")
	private boolean passed;
	
	@Column(name = "score")
	private double score;
	
	@Column(name = "grade")
	private int grade;
	
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
	private Student student;
	
	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "course_id", nullable = false)
	private Course course;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enrollment")
	private Set<ExamObligationTaking> examObligationTakings;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "enrollment")
	private Set<ExamTaking> examTakings;

}
