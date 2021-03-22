package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;

public interface ExamObligationTakingRepository extends JpaRepository<ExamObligationTaking, Integer> {
	
	@Query("SELECT eot from ExamObligationTaking eot WHERE "
			+ "(:examObligationId is null OR eot.examObligation.id = :examObligationId) AND "
			+ "(:enrollmentId is null OR eot.enrollment.id = :enrollmentId) AND "
			+ "(:scoreFrom is null OR eot.score >= :scoreFrom) AND "
			+ "(:scoreTo is null OR eot.score <= :scoreTo)")
	Page<ExamObligationTaking> filterExamObligationTakings(@Param("examObligationId") Integer examObligationId, 
									@Param("enrollmentId") Integer enrollmentId, 
									@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, 
									Pageable pageable);

}
