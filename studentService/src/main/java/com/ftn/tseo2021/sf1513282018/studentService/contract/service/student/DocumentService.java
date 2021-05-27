package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;

public interface DocumentService extends BaseService<DefaultDocumentDTO, Integer> {
	
	Page<StudentDocumentDTO> filterDocuments(int studentId, Pageable pageable, StudentDocumentDTO filterOptions);
}
