package com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.institution.DefaultInstitutionDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.user.DefaultUserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultTeacherDTO implements TeacherDTO {

	private Integer id;
    private String firstName;
    private String lastName;
    private String address;
    private LocalDate dateOfBirth;
    private DefaultTeacherTitleDTO teacherTitle;
    private DefaultUserDTO user;
    private DefaultInstitutionDTO institution;
}
