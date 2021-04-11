package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeachingRepository extends JpaRepository<Teaching, Integer> {
	
	Page<Teaching> findByTeacher_Id(int teacherId, Pageable pageable);

    @Query("Select t from Teaching t WHERE " +
            "(:teacherId is null OR t.teacher.id = :teacherId) AND " +
            "(:courseId is null OR t.course.id = :courseId) AND " +
            "(:teacherRoleId is null OR t.teacherRole.id = :teacherRoleId)")
    Page<Teaching> filterTeachings(@Param("teacherId") Integer teacherId, @Param("courseId") Integer courseId,
                                   @Param("teacherRoleId") Integer teacherRoleId, Pageable pageable);
}
