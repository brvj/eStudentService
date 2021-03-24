package com.ftn.tseo2021.sf1513282018.studentService.model.dto.course;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.course.CourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultCourseDTO implements CourseDTO{
    public Integer id;
    public String name;
    public DefaultInstitutionDTO institution;
}
