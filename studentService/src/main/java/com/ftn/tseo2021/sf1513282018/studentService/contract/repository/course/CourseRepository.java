package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;


public interface CourseRepository extends JpaRepository<Course, Integer> {
    Course findByName(String name);

    Set<Course> findAllByInstitution(Institution institution);

    @Query(value = "Select * from course c where c.course_id in (select t.course_id from teaching t where" +
            "t.course_id = ?1)", nativeQuery = true)
    Course findByTeachingId(int teachingId);

    @Query(value = "Select * from course c where c.course_id in (select en.course_id from enrollment en where" +
            "en.course_id = ?1)", nativeQuery = true)
    Course findByEnrollmentId(int enrollmentId);

    @Query(value = "Select * from course c where c.course_id in (select eo.course_id from exam_obligation eo where" +
            "eo.course_id = ?1)", nativeQuery = true)
    Course findByExamObligationId(int examObligationId);

    @Query(value = "Select * from course c where c.course_id in (select e.course_id from exam e where" +
            "e.course_id = ?1)", nativeQuery = true)
    Course findByExamId(int examId);
}
