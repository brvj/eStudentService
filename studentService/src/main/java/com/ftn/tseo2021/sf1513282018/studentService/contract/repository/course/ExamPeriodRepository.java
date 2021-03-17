package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;


import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface ExamPeriodRepository extends JpaRepository<ExamPeriod, Integer> {
    Set<ExamPeriod> findAllByInstitution(Institution institution);

    @Query(value = "Select * from exam_period ep where ep.exam_period_id in (select e.exam_period_id from exam e where " +
            "e.exam_id = ?1)", nativeQuery = true)
    ExamPeriod findByExamId(int examId);

    @Query("select ep from ExamPeriod ep where " +
            "ep.institution.id = :institutionId and " +
            "(:name is null or lower(ep.name) like lower(concat('%', :name, '%'))) and " +
            "(:startDate is null or ep.endDate >= :startDate) and " +
            "(:endDate is null or ep.startDate <= :endDate)")
    Page<ExamPeriod> filterExamPeriods(@Param("institutionId") int institutionId, @Param("name") String name,
                                       @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate,
                                       Pageable pageable);
}
