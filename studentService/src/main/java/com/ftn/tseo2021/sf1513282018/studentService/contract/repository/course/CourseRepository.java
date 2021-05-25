package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.course.Course;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	Page<Course> findByInstitution_Id(int institutionId, Pageable pageable);

    @Query("select c from Course c where " +
            "c.institution.id = :institutionId and " +
            "(:name is null or lower(c.name) like lower(concat('%', :name, '%')))")
    Page<Course> filterCourses(@Param("institutionId") int institutionId, @Param("name") String name,
                               Pageable pageable);

}
