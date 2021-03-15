package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;


import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;

import java.time.LocalDate;
import java.util.Set;

@Repository
public interface ExamPeriodRepository extends JpaRepository<ExamPeriod, Integer> {
    Set<ExamPeriod> findAllByInstitution(Institution institution);

    @Query(value = "Select * from exam_period ep where ep.exam_period_id in (select e.exam_period_id from exam e where" +
            "e.exam_id = ?1)", nativeQuery = true)
    ExamPeriod findByExamId(int examId);

    Set<ExamPeriod> findAllByStartDateAfter(LocalDate startDateFrom);

    Set<ExamPeriod> findAllByEndDateBefore(LocalDate endDateTo);

    Set<ExamPeriod> findAllByStartDateAfterAndEndDateBefore(LocalDate startDateFrom, LocalDate endDateTo);
}
