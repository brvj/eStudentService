package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "exam_obligation_taking")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExamObligationTaking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "exam_obligation_taking_id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "score")
	private double score;
	
	@ManyToOne
	@JoinColumn(name = "enrollment_id", referencedColumnName = "enrollment_id", nullable = false)
	private Enrollment enrollment;
	
	@ManyToOne
	@JoinColumn(name = "exam_obligation_id", referencedColumnName = "exam_obligation_id", nullable = false)
	private ExamObligation examObligation;

}
