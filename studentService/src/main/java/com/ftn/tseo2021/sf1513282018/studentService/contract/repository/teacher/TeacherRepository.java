package com.ftn.tseo2021.sf1513282018.studentService.contract.repository.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.model.jpa.teacher.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

    Optional<Teacher> findByUser_Id(int userId);
}
