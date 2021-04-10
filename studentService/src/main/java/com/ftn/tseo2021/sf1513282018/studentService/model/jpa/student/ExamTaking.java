package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "exam_taking")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExamTaking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exam_taking_id", unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "score")
	private double score;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "enrollment_id", referencedColumnName = "enrollment_id", nullable = false)
	private Enrollment enrollment;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "exam_id", referencedColumnName = "exam_id", nullable = false)
	private Exam exam;

}
