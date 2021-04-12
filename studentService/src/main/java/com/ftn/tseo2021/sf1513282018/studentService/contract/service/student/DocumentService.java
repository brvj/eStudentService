package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;


import java.util.List;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;

public interface DocumentService extends BaseService<DefaultDocumentDTO, Integer> {
	
	List<StudentDocumentDTO> getByStudentId(int studentId, Pageable pageable);

}
