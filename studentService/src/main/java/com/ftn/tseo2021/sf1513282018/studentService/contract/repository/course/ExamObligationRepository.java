package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;

@Repository
public interface ExamObligationRepository extends JpaRepository<ExamObligation, Integer> {
	
	Page<ExamObligation> findByCourse_Id(int courseId, Pageable pageable);

    @Query("select eo from ExamObligation eo where " +
            "eo.course.id = :courseId and " +
            "(:description is null or lower(eo.description) like lower(concat('%', :description, '%'))) and " +
            "(:examObligationTypeId is null or (eo.examObligationType.id = :examObligationTypeId))")
    Page<ExamObligation> filterExamObligations(@Param("courseId") int courseId, @Param("description") String description,
                                               @Param("examObligationTypeId") Integer examObligationTypeId,
                                               Pageable pageable);

}
