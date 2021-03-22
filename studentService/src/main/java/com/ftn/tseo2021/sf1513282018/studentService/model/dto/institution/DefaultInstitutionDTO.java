package com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.institution.InstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultCourseDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.course.DefaultExamPeriodDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher.DefaultTeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultInstitutionDTO implements InstitutionDTO {

    private Integer id;
    private String name;
    private String address;
    private String phoneNumber;
    private List<DefaultUserDTO> users;
    private List<DefaultTeacherDTO> teachers;
    private List<DefaultStudentDTO> students;
    private List<DefaultExamPeriodDTO> examPeriods;
    private List<DefaultCourseDTO> courses;
}
