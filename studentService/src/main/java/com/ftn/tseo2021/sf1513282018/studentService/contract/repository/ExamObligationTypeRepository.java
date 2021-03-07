package com.ftn.tseo2021.sf1513282018.studentService.contract.repository;

import com.ftn.tseo2021.sf1513282018.studentService.entity.ExamObligationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamObligationTypeRepository extends JpaRepository<ExamObligationType, Integer> {
}
