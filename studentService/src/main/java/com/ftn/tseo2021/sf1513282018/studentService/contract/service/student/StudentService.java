package com.ftn.tseo2021.sf1513282018.studentService.contract.service.student;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ftn.tseo2021.sf1513282018.studentService.contract.service.common.BaseService;
import com.ftn.tseo2021.sf1513282018.studentService.exceptions.PersonalizedAccessDeniedException;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultFinancialCardDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.DefaultStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.InstitutionStudentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentDocumentDTO;
import com.ftn.tseo2021.sf1513282018.studentService.model.dto.student.StudentEnrollmentDTO;

public interface StudentService extends BaseService<DefaultStudentDTO, Integer> {
	
	DefaultStudentDTO getByUserId(int userId);
	
	Page<InstitutionStudentDTO> filterStudents(int institutionId, Pageable pageable, DefaultStudentDTO filterOptions) throws PersonalizedAccessDeniedException;
	
	Page<StudentEnrollmentDTO> getStudentEnrollments(int studentId, Pageable pageable) throws EntityNotFoundException;
	
	Page<StudentDocumentDTO> getStudentDocuments(int studentId, Pageable pageable) throws EntityNotFoundException;
	
	DefaultFinancialCardDTO getStudentFinancialCard(int studentId) throws EntityNotFoundException;
}
