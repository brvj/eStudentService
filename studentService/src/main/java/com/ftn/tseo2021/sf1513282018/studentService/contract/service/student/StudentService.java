package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultEnrollmentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;

public interface StudentService extends BaseService<DefaultStudentDTO, Integer> {
	
	DefaultStudentDTO getByUserId(int userId);
	
	List<InstitutionStudentDTO> getByInstitutionId(int institutionId, Pageable pageable);
	
	List<DefaultEnrollmentDTO> getStudentEnrollments(int studentId, Pageable pageable) throws EntityNotFoundException;
	
	List<DefaultDocumentDTO> getStudentDocuments(int studentId, Pageable pageable) throws EntityNotFoundException;
	
	DefaultFinancialCardDTO getStudentFinancialCard(int studentId) throws EntityNotFoundException;
}
