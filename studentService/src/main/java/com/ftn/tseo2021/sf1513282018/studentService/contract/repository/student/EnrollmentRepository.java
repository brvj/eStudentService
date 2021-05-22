package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.student;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {
	
	Page<Enrollment> findByStudent_Id(int studentId, Pageable pageable);
	
	Page<Enrollment> findByCourse_Id(int courseId, Pageable pageable);
	
	@Query("SELECT e from Enrollment e WHERE "
			+ "e.student.id = :studentId AND "
			+ "(:startDate is null OR e.startDate >= :startDate) AND "
			+ "(:endDate is null OR e.startDate <= :endDate) AND "
			+ "(:passed is null OR e.passed = passed) AND "
			+ "(:scoreFrom is null OR e.score >= scoreFrom) AND "
			+ "(:scoreTo is null OR e.score <= scoreTo) AND "
			+ "(:gradeFrom is null OR e.grade >= gradeFrom) AND "
			+ "(:gradeTo is null OR e.grade <= gradeTo)")
	Page<Enrollment> filterEnrollmentsByStudent(@Param("studentId") int studentId, 
							@Param("startDate") LocalDate startStartDate, 
							@Param("endDate") LocalDate endStartDate, 
							@Param("passed") Boolean passed, 
							@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, 
							@Param("gradeFrom") Integer gradeFrom, @Param("gradeTo") Integer gradeTo, 
							Pageable pageable);
	
	@Query("SELECT e from Enrollment e WHERE "
			+ "e.course.id = :courseId AND "
			+ "(:startDate is null OR e.startDate >= :startDate) AND "
			+ "(:endDate is null OR e.startDate <= :endDate) AND "
			+ "(:passed is null OR e.passed = passed) AND "
			+ "(:scoreFrom is null OR e.score >= scoreFrom) AND "
			+ "(:scoreTo is null OR e.score <= scoreTo) AND "
			+ "(:gradeFrom is null OR e.grade >= gradeFrom) AND "
			+ "(:gradeTo is null OR e.grade <= gradeTo)")
	Page<Enrollment> filterEnrollmentsByCourse(@Param("courseId") int courseId, 
							@Param("startDate") LocalDate startStartDate, 
							@Param("endDate") LocalDate endStartDate, 
							@Param("passed") Boolean passed, 
							@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, 
							@Param("gradeFrom") Integer gradeFrom, @Param("gradeTo") Integer gradeTo, 
							Pageable pageable);
	
	@Query("SELECT e from Enrollment e WHERE "
			+ "(:studentId is null OR e.student.id = :studentId) AND "
			+ "(:courseId is null OR e.course.id = :courseId) AND "
			+ "(:startDate is null OR e.startDate >= :startDate) AND "
			+ "(:endDate is null OR e.startDate <= :endDate) AND "
			+ "(:passed is null OR e.passed = passed) AND "
			+ "(:scoreFrom is null OR e.score >= scoreFrom) AND "
			+ "(:scoreTo is null OR e.score <= scoreTo) AND "
			+ "(:gradeFrom is null OR e.grade >= gradeFrom) AND "
			+ "(:gradeTo is null OR e.grade <= gradeTo)")
	Page<Enrollment> filterEnrollments(@Param("studentId") Integer studentId, 
							@Param("courseId") Integer courseId, 
							@Param("startDate") LocalDate startStartDate, 
							@Param("endDate") LocalDate endStartDate, 
							@Param("passed") Boolean passed, 
							@Param("scoreFrom") Double scoreFrom, @Param("scoreTo") Double scoreTo, 
							@Param("gradeFrom") Integer gradeFrom, @Param("gradeTo") Integer gradeTo, 
							Pageable pageable);
	
	boolean existsByStudent_IdAndCourse_Id(int studentId, int courseId);

}
