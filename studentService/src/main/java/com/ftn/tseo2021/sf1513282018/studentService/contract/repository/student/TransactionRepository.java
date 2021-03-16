package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

    //filter(int financialCardId, TransactionType type, LocalDate startTransactionDate, 
	//LocalDate endTransactionDate, String description, Pageable pageable);
}
