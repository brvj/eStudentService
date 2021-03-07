package com.ftn.tseo2021.sf1513282018.studentService.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "financialCard")
@Data
public class FinancialCard {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id", unique = true)
    private int id;
	
	@Column(name = "currentAmmount")
	private double currentAmmout;
	
	@Column(name = "totalDeposit")
	private double totalDeposit;
	
	@Column(name = "totalSpent")
	private double totalSpent;
	
	
}
