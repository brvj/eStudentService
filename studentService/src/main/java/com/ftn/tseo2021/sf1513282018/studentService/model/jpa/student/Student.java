package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.*;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Student {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", unique = true, nullable = false)
    private int id;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "student_card")
	private String studentCard;
	
	@Column(name = "address")
	private String address;
	    
    @Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
    
    @Column(name = "generation")
	private int generation;

	@ManyToOne
	@JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
	private Institution institution;
	
	@OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	private Set<Document> documents;
	
	@OneToOne
	@JoinColumn(name = "financial_card_id", referencedColumnName = "financial_card_id", nullable = false)
	private FinancialCard financialCard;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "student")
	private Set<Enrollment> enrollments;
}
