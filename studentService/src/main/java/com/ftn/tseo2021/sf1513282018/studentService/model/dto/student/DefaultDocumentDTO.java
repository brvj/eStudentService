package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.DocumentDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultDocumentDTO implements DocumentDTO {

	private static final long serialVersionUID = 947628414904513089L;
	
	private Integer id;
	private String name;
	private String type;
	private String path;
	private DefaultStudentDTO student;
}
