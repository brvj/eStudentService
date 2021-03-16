package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.institution;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.institution.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface InstitutionRepository extends JpaRepository<Institution, Integer> {

    Set<Institution> findByNameContaining(String name);

    Set<Institution> findByAddressContaining(String address);

    @Query(value = "Select * from institution i where i.institution_id in (select t.institution_id from teacher t where t.teacher_id = ?1)", nativeQuery = true)
    Institution findByTeacherId(int teacherId);

    @Query(value = "Select * from institution i where i.institution_id in (select s.institution_id from student s where s.student_id = ?1)", nativeQuery = true)
    Institution findByStudentId(int studentId);

    @Query(value = "Select * from institution i where i.institution_id in (select s.institution_id from student s where s.student_card like concat('%', ?1 , '%'))", nativeQuery = true)
    Institution findByStudentCard(String studentCard);

    @Query(value = "Select * from institution i where i.institution_id in (select u.institution_id from user u where u.user_id = ?1 and u.user_type = 0)", nativeQuery = true)
    Institution findByAdminId(int adminId);

    @Query(value = "Select * from institution i where i.institution_id in (select c.institution_id from course c where c.course_id = ?1)", nativeQuery = true)
    Institution findByCourseId(int courseId);

    @Query(value = "Select * from institution i where i.institution_id in (select c.institution_id from course c where c.name like concat('%', ?1 , '%'))", nativeQuery = true)
    Set<Institution> findByCourseName(String name);
}
