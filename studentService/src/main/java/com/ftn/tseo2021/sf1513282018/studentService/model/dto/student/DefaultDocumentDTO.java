package com.ftn.tseo2021.sf1513282018.studentService.model.dto.student;

import com.ftn.tseo2021.sf1513282018.studentService.contract.dto.student.DocumentDTO;

public class DefaultDocumentDTO implements DocumentDTO {

	private Integer id;
	private String name;
	private String type;
	private String path;
	private DefaultStudentDTO student;
}
