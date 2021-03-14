package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.TeacherTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface TeacherTitleRepository extends JpaRepository<TeacherTitle, Integer> {

    Set<TeacherTitle> findByNameContaining(String name);

    @Query(value = "Select * from teacher_title tt where tt.teacher_title_id in (select t.teacher_title_id from teacher t where t.teacher_id = ?1)", nativeQuery = true)
    TeacherTitle findByTeacherId(int teacherId);

    @Query(value = "Select * from teacher_title tt where tt.teacher_title_id in (select t.teacher_title_id from teacher t where t.teacher_id = ?1 and t.institution_id = ?2)", nativeQuery = true)
    TeacherTitle findByTeacherIdAndInstitutionId(int teacherId, int institutionId);

    @Query(value = "Select * from teacher_title tt where tt.teacher_title_id in " +
            "(select t.teacher_title_id from teacher t join teacher_teaching_course ttc on t.teacher_id = ttc.teacher_id = ?1 and ttc.course_id = ?2)", nativeQuery = true)
    TeacherTitle findByTeacherIdAndCourseId(int teacherId, int courseId);
}
