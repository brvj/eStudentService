package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "financial_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinancialCard {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "financial_card_id", unique = true, nullable = false)
    private int id;
	
	@Column(name = "current_ammount")
	private double currentAmmout;
	
	@Column(name = "total_deposit")
	private double totalDeposit;
	
	@Column(name = "total_spent")
	private double totalSpent;
	
	
}
