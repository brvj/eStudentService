package com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher;

import java.util.List;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherRoleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultTeacherRoleDTO implements TeacherRoleDTO {

    private Integer id;
    private String name;
    private List<DefaultTeachingDTO> teachings;
}
