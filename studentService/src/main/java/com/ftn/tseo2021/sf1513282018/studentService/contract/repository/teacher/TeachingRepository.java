package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TeachingRepository extends JpaRepository<Teaching, Integer> {
	
	Page<Teaching> findByTeacher_Id(int teacherId, Pageable pageable);
	
	Page<Teaching> findByCourse_Id(int courseId, Pageable pageable);

    @Query("Select t from Teaching t WHERE " +
            "t.teacher.id = :teacherId AND " +
            "(:teacherRoleId is null OR t.teacherRole.id = :teacherRoleId)")
    Page<Teaching> filterTeachingsByTeacher(@Param("teacherId") int teacherId,
                                   @Param("teacherRoleId") Integer teacherRoleId, Pageable pageable);
    
    @Query("Select t from Teaching t WHERE " +
            "t.course.id = :courseId AND " +
            "(:teacherRoleId is null OR t.teacherRole.id = :teacherRoleId)")
    Page<Teaching> filterTeachingsByCourse(@Param("courseId") int courseId,
                                   @Param("teacherRoleId") Integer teacherRoleId, Pageable pageable);
    
    boolean existsByTeacher_IdAndCourse_Id(int teacherId, int courseId);
}
