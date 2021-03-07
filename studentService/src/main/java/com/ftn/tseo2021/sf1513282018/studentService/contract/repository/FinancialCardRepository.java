package com.ftn.tseo2021.sf1513282018.studentService.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.entity.FinancialCard;

@Repository
public interface FinancialCardRepository extends JpaRepository<FinancialCard, Integer> {

}
