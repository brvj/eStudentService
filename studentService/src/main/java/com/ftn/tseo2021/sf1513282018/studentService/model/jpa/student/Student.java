package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.Institution;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", unique = true, nullable = false)
    private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
	private Institution institution;
}
