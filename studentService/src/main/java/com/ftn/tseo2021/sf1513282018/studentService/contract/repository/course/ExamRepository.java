package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

import java.time.LocalDate;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
    @Query(value = "Select * from exam e where e.exam_id in (select et.exam_id from exam_taking et where" +
            "et.exam_taking_id = ?1)", nativeQuery = true)
    Exam findByExamTakingId(int examTakingId);

    @Query("select e from Exam e where " +
            "e.course.id = :courseId and " +
            "e.examPeriod.id = :examPeriodId and " +
            "(:classroom is null or e.classroom like concat('%', :name, '%')) and " +
            "(:startDate is null or e.dateTime isAfter :startDate) and " +
            "(:endDate is null or e.dateTime isBefore :endDate)")
    Page<Exam> filterExams(@Param("courseId") Integer courseId, @Param("examPeriodId") Integer examPeriodId,
                           @Param("classroom") String classroom, @Param("startDate") LocalDate startDate,
                           @Param("endDate") LocalDate endDate, Pageable pageable);
}
