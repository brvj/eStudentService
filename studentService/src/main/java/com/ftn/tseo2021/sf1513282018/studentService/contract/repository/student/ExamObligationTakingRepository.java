package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamObligationTaking;

public interface ExamObligationTakingRepository extends JpaRepository<ExamObligationTaking, Integer> {
	
	Page<ExamObligationTaking> findByEnrollment_Id(int enrollmentId, Pageable pageable);
	
	Page<ExamObligationTaking> findByExamObligation_Id(int examObligationId, Pageable pageable);
	
	@Query("SELECT eot from ExamObligationTaking eot WHERE "
			+ "eot.enrollment.id = :enrollmentId AND "
			+ "(:scoreFrom is null OR eot.score >= :scoreFrom) AND "
			+ "(:scoreTo is null OR eot.score <= :scoreTo)")
	Page<ExamObligationTaking> filterTakingsByEnrollment(@Param("enrollmentId") int enrollmentId, 
									@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, 
									Pageable pageable);
	
	@Query("SELECT eot from ExamObligationTaking eot WHERE "
			+ "eot.examObligation.id = :examObligationId AND "
			+ "(:scoreFrom is null OR eot.score >= :scoreFrom) AND "
			+ "(:scoreTo is null OR eot.score <= :scoreTo)")
	Page<ExamObligationTaking> filterTakingsByExamObligation(@Param("examObligationId") int examObligationId, 
									@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, 
									Pageable pageable);

}
