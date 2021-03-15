package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;

import java.util.Set;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Integer> {
    Set<Exam> findAllByExamPeriod(ExamPeriod examPeriod);

    Set<Exam> findAllByCourse(Course course);

    @Query(value = "Select * from exam e where e.exam_id in (select et.exam_id from exam_taking et where" +
            "et.exam_taking_id = ?1)", nativeQuery = true)
    Exam findByExamTakingId(int examTakingId);
}
