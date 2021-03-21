package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Exam;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.ExamObligation;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.student.Enrollment;
import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByTeachingsContaining(Teaching teachings);

    Optional<Course> findByEnrollmentsContaining(Enrollment enrollment);

    Optional<Course> findByExamObligationsContaining(ExamObligation examObligation);

    Optional<Course> findByExamsContaining(Exam exam);

    @Query("select c from Course c where " +
            "c.institution.id = :institutionId and " +
            "(:name is null or lower(c.name) like lower(concat('%', :name, '%')))")
    Page<Course> filterCourses(@Param("institutionId") int institutionId, @Param("name") String name,
                               Pageable pageable);
}
