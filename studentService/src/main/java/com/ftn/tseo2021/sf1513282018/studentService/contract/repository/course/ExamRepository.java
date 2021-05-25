package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

import java.time.LocalDateTime;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
	
	Page<Exam> findByCourse_Id(int courseId, Pageable pageable);
	
	Page<Exam> findByExamPeriod_Id(int examPeriodId, Pageable pageable);
	
	@Query("select e from Exam e where " +
            "e.course.id = :courseId and " +
            "(:description is null or lower(e.description) like lower(concat('%', :description, '%'))) and " +
            "(:classroom is null or e.classroom like concat('%', :classroom, '%')) and " +
            "(:startDate is null or e.dateTime >= :startDate) and " +
            "(:endDate is null or e.dateTime <= :endDate)")
    Page<Exam> filterExamsByCourse(@Param("courseId") int courseId,
    		 				@Param("description") String description,
                           @Param("classroom") String classroom, @Param("startDate") LocalDateTime startDate,
                           @Param("endDate") LocalDateTime endDate, Pageable pageable);

    @Query("select e from Exam e where " +
            "e.examPeriod.id = :examPeriodId and " +
            "(:description is null or lower(e.description) like lower(concat('%', :description, '%'))) and " +
            "(:classroom is null or e.classroom like concat('%', :classroom, '%')) and " +
            "(:startDate is null or e.dateTime >= :startDate) and " +
            "(:endDate is null or e.dateTime <= :endDate)")
    Page<Exam> filterExamsByExamPeriod(@Param("examPeriodId") int examPeriodId,
    		 				@Param("description") String description,
                           @Param("classroom") String classroom, @Param("startDate") LocalDateTime startDate,
                           @Param("endDate") LocalDateTime endDate, Pageable pageable);

	// Page<Exam> findByTeaching_IdAndCourse_Id(int teacherId, int courseId);
}
