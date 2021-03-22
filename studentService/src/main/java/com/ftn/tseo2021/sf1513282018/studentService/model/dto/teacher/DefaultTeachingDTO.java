package com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeachingDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultTeachingDTO implements TeachingDTO {

    private Integer id;
    private LocalDate startDate;
    private DefaultTeacherDTO teacher;
    private DefaultTeacherRoleDTO teacherRole;
    private DefaultCourseDTO course;
}
