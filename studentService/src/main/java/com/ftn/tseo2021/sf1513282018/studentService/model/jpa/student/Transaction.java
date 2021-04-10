package com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ftn.tseo2021.sf1513282018.studentService.model.common.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id", unique = true, nullable = false)
    private int id;
	
	@Column(name = "ammount")
	private double ammount;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "transaction_type")
	@Enumerated(EnumType.ORDINAL)
	private TransactionType transactionType;
	
	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	@JoinColumn(name = "financial_card_id", referencedColumnName = "financial_card_id", nullable = false)
	private FinancialCard financialCard;
	
}
