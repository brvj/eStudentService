package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.common.TransactionType;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Student;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	@Query("SELECT t from Transaction t WHERE " +
			"t.financialCard.id = :financialCardId AND " +
			"(:transactionType is null OR t.transactionType = :transactionType) AND " + 
			"(:startTransactionDate is null OR t.date >= :startTransactionDate) AND " + 
			"(:endTransactionDate is null OR t.date >= :endTransactionDate) AND " +
			"(:description is null OR lower(t.description) LIKE lower(CONCAT('%', :description,'%')))")
	Page<Transaction> filterTransaction(@Param("financialCardId") int financialCardId, @Param("transactionType") TransactionType transactionType,
            @Param("startTransactionDate") LocalDate startTransactionDate, @Param("endTransactionDate") LocalDate endTransactionDate,
            @Param("description") String description, Pageable pageable);
}
