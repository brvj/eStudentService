package com.ftn.tseo2021.sf1513282018.studentService.contract.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

@Repository
public interface ExamObligationRepository extends JpaRepository<ExamObligation, Integer> {
}
