package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TeacherRoleRepository extends JpaRepository<TeacherRole, Integer> {

    Set<TeacherRole> findByNameContaining(String name);

    @Query(value = "Select * from teacher_role tr where tr.teacher_role_id in " +
            "(select ttc.teacher_role_id from teacher_teaching_course ttc where ttc.teacher_id = ?1)", nativeQuery = true)
    Set<TeacherRole> findByTeacherId(int teacherId);

    @Query(value = "Select * from teacher_role tr where tr.teacher_role_id in " +
            "(select ttc.teacher_role_id from teacher_teaching_course ttc where ttc.course_id = ?1)", nativeQuery = true)
    Set<TeacherRole> findByCourseId(int courseId);

    @Query(value = "Select * from teacher_role tr where tr.teacher_role_id in " +
            "(select ttc.teacher_role_id from teacher_teaching_course ttc where ttc.teacher_id = ?1 and ttc.course_id = ?2)", nativeQuery = true)
    Set<TeacherRole> findByTeacherIdAndCourseId(int teacherId, int courseId);
}
