package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "financial_card")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
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
	
	@OneToOne(mappedBy = "financialCard")
	private Student student;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "financialCard")
	private Set<Transaction> transactions;
	
}
