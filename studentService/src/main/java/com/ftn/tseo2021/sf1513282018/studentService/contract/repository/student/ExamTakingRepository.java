package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;

public interface ExamTakingRepository extends JpaRepository<ExamTaking, Integer> {
	
	Page<ExamTaking> findByEnrollment_Id(int enrollmentId, Pageable pageable);
	
	Page<ExamTaking> findByExam_Id(int examId, Pageable pageable);
	
	@Query("SELECT et from ExamTaking et WHERE "
			+ "et.enrollment.id = :enrollmentId AND "
			+ "(:scoreFrom is null OR et.score >= :scoreFrom) AND "
			+ "(:scoreTo is null OR et.score <= :scoreTo)")
	Page<ExamTaking> filterTakingsByEnrollment(@Param("enrollmentId") int enrollmentId, 
			@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, Pageable pageable);
	
	@Query("SELECT et from ExamTaking et WHERE "
			+ "et.exam.id = :examId AND "
			+ "(:scoreFrom is null OR et.score >= :scoreFrom) AND "
			+ "(:scoreTo is null OR et.score <= :scoreTo)")
	Page<ExamTaking> filterTakingsByExam(@Param("examId") int examId, 
			@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, Pageable pageable);

}
