package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "financial_card")
@Getter
@Setter
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
	
	@OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id", referencedColumnName = "student_id", nullable = false)
	private Student student;
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY, mappedBy = "financialCard")
	private Set<Transaction> transactions;
	
}
