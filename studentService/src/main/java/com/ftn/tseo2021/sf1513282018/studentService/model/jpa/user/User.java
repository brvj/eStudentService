package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.user;

import javax.persistence.*;
import javax.validation.constraints.Email;

import com.ftn.tseo2021.sf1513282018.studentService.model.common.UserType;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Table(name = "user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id", unique = true, nullable = false)
	private int id;
	
	@Column(name = "username", unique = true, nullable = false)
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Email
	@Column(name = "email")
	private String email;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "user_type")
	@Enumerated(EnumType.ORDINAL)
	private UserType userType;

	@ManyToOne
	@JoinColumn(name = "institution_id", referencedColumnName = "institution_id", nullable = false)
	private Institution institution;
	
}
