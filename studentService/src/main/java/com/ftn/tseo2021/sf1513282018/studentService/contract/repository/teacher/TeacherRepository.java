package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByUser_Id(int userId);

    @Query("SELECT t from Teacher t WHERE " +
            "t.institution.id = :institutionId AND " +
            "(:teacherTitleId is null OR t.teacherTitle.id = :teacherTitleId) AND " +
            "(:firstName is null OR lower(t.firstName) LIKE lower(CONCAT('%', :firstName, '%'))) AND " +
            "(:lastName is null OR lower(t.lastName) LIKE lower(CONCAT('%', :lastName, '%')))")
    Page<Teacher> filterTeachers(@Param("institutionId") int institutionId, @Param("teacherTitleId") Integer teacherTitleId,
                                 @Param("firstName") String firstName, @Param("lastName") String lastName, Pageable pageable);
}
