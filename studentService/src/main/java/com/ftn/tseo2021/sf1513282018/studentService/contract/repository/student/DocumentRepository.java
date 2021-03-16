package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    //filter(int studentId, String name, Pageable pageable);

}
