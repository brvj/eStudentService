package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.Institution;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity(name = "course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
	private Institution institution;
}
