package com.ftn.tseo2021.sf1513282018.studentService.model.dto.teacher;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.teacher.TeacherTitleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultTeacherTitleDTO implements TeacherTitleDTO {

	private static final long serialVersionUID = -5108426598452837550L;
	
	private Integer id;
    private String name;
}
