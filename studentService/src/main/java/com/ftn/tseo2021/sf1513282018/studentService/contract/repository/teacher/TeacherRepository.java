package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Set<Teacher> findByFirstName(String firstName);

    Set<Teacher> findByLastName(String lastName);

    Set<Teacher> findByAddress(String address);

    @Query(value = "Select * from teacher t where t.user_id = ?1", nativeQuery = true)
    Teacher findByUserId(int userId);

    @Query(value = "Select * from teacher t where t.institution_id = ?1;", nativeQuery = true)
    Set<Teacher> findByInstitutionId(int institutionId);

    @Query(value = "Select * from teacher t where t.institution_id in (select i.institution_id from institution i where i.name like concat('%', ?1 , '%'))", nativeQuery = true)
    Set<Teacher> findByInstitutionName(String name);

    @Query(value = "Select * from teacher t where t.teacher_title_id = ?1", nativeQuery = true)
    Set<Teacher> findByTeacherTitleId(int teacherTitleId);

    @Query(value = "Select * from teacher t where t.teacher_title_id in " +
            "(select tt.teacher_title_id from teacher_title tt where tt.name like concat('%', ?1 , '%'))", nativeQuery = true)
    Set<Teacher> findByTeacherTitleName(String name);

    @Query(value = "Select * from teacher t where t.teacher_id in " +
            "(select ttc.teacher_id from teacher_teaching_course ttc where ttc.course_id = ?1)", nativeQuery = true)
    Set<Teacher> findByCourseId(int courseId);

    @Query(value = "Select * from teacher t where t.teacher_id in " +
            "(select ttc.teacher_id from teacher_teaching_course ttc join course c on ttc.course_id = c.course_id where c.name like concat('%', ?1 , '%'))", nativeQuery = true)
    Set<Teacher> findByCourseName(String name);

    @Query(value = "Select * from teacher t where t.teacher_id in " +
            "(select ttc.teacher_id from teacher_teaching_course ttc where ttc.teacher_role_id = ?1)", nativeQuery = true)
    Set<Teacher> findByTeacherRoleId(int teacherRoleId);

    @Query(value = "Select * from teacher t where t.teacher_id in " +
            "(select ttc.teacher_id from teacher_teaching_course ttc join teacher_role tr on ttc.teacher_role_id = tr.teacher_role_id where tr.name like concat('%', ?1 , '%'))", nativeQuery = true)
    Set<Teacher> findByTeacherRoleName(String name);

    @Query(value = "Select * from teacher t where t.teacher_id in " +
            "(select ttc.teacher_id from teacher_teaching_course ttc where ttc.course_id = ?1) and t.institution_id = ?2", nativeQuery = true)
    Set<Teacher> findByCourseIdAndInstitutionId(int courseId, int institutionId);
}
