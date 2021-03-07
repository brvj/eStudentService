package com.ftn.tseo2021.sf1513282018.studentService.entity;



import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", unique = true)
    private int id;
	
	@Column(name = "ammount")
	private double ammount;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "description")
	private String description;
	
}
