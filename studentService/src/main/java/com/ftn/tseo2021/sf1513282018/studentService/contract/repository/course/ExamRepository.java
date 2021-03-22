package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.ExamTaking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
    Optional<Exam> findByExamTakingsContaining(ExamTaking examTaking);

    @Query("select e from Exam e where " +
            "(:courseId is null or e.course.id = :courseId) and " +
            "(:examPeriodId is null or e.examPeriod.id = :examPeriodId) and " +
            "(:description is null or lower(e.description) like lower(concat('%', :description, '%'))) and " +
            "(:classroom is null or e.classroom like concat('%', :name, '%')) and " +
            "(:startDate is null or e.dateTime >= :startDate) and " +
            "(:endDate is null or e.dateTime <= :endDate)")
    Page<Exam> filterExams(@Param("courseId") Integer courseId, @Param("examPeriodId") Integer examPeriodId,
    		 				@Param("description") String description,
                           @Param("classroom") String classroom, @Param("startDate") LocalDateTime startDate,
                           @Param("endDate") LocalDateTime endDate, Pageable pageable);
}
