package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeachingRepository extends JpaRepository<Teaching, Integer> {

    Page<Teaching> filter();
}
