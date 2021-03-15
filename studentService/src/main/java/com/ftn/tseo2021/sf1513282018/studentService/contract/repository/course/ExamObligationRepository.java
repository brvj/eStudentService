package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligationType;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

import java.util.Set;

@Repository
public interface ExamObligationRepository extends JpaRepository<ExamObligation, Integer> {
    Set<ExamObligation> findAllByCourse(Course course);

    Set<ExamObligation> findAllByExamObligationType(ExamObligationType examObligationType);

    ExamObligation findByExamObligationTaking(ExamObligationTaking examObligationTaking);
}
